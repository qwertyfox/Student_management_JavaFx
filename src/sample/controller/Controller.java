package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import sample.dao.DbAccess;
import sample.model.Student;
import sample.model.Subject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML
    public TextField lNTextFieldId;
    @FXML
    public TextField fNTextFieldId;
    @FXML
    public BorderPane mainWindowID;
    @FXML
    private TableView<Student> tableViewID;
    @FXML
    private ProgressBar progressBarId;
    @FXML
    private TextArea textAreaId;
    @FXML
    private ContextMenu contextMenu;

     public void loadMainUI() {
        contextMenu = new ContextMenu();

        MenuItem showEnrollment = new MenuItem("Show Enrollment");
        MenuItem generateReport = new MenuItem("Generate Report");
        MenuItem deleteStudent = new MenuItem("Delete");
        showEnrollment.setOnAction(event -> {
            Student student = tableViewID.getSelectionModel().getSelectedItem();
            studentEnrollment(student);
        });
        generateReport.setOnAction(event -> {
            Student student = tableViewID.getSelectionModel().getSelectedItem();
            generateReportAlert(student);
        });
        deleteStudent.setOnAction(event -> {
            Student student = tableViewID.getSelectionModel().getSelectedItem();
            try {
                deleteStudent(student);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        contextMenu.getItems().addAll(showEnrollment,generateReport,deleteStudent);


        Task<ObservableList<Student>> task = new LoadStudents();
        new Thread(task).start();

        // Binding data with UI
        tableViewID.itemsProperty().bind(task.valueProperty());
        progressBarId.visibleProperty().bind(task.runningProperty());
        progressBarId.progressProperty().bind(task.progressProperty());

        tableViewID.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableViewID.getSelectionModel().selectFirst();

        tableViewID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Student student = (Student) tableViewID.getSelectionModel().getSelectedItem();
                String sb = "Student ID : " + student.getStudent_id() + "\n" +
                        "First name : " + student.getName() + "\n" +
                        "Last name : " + student.getLast_name().toUpperCase() + "\n" +
                        "Contact # " + student.getContact_number();
                textAreaId.setText(sb);
            }
        });
        tableViewID.setRowFactory(param -> {
            TableRow<Student> tableRow = new TableRow<>();

            tableRow.emptyProperty().addListener((observable, isNotEmpty, isEmpty) -> {
                if (isEmpty) {
                    tableRow.setContextMenu(null);
                } else {
                    tableRow.setContextMenu(contextMenu);
                }
            });
            return tableRow;
        });
    }

    public void searchStudentFName() {
        String studentName = fNTextFieldId.getText();
        Student student = DbAccess.getInstance().searchStudentFirstName(studentName);
        if (student == null) {
            textAreaId.setText("Student " + studentName + " does not exist in the Database");
        } else {
            tableViewID.getSelectionModel().select(student);
        }
    }

    public void searchStudentLName() {
        String studentName = lNTextFieldId.getText();
        Student student = DbAccess.getInstance().searchStudentLastName(studentName);
        if (student == null) {
            textAreaId.setText("Student " + studentName + " does not exist in the Database");
        } else {
            tableViewID.getSelectionModel().select(student);
        }
    }

    public void clearFNTextField() {
        fNTextFieldId.clear();
    }

    public void clearLNTextField() {
        lNTextFieldId.clear();
    }

    public void studentEnrollment(Student student) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindowID.getScene().getWindow());
        dialog.setTitle("Student enrollment details");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/fxml/EnrollmentDetailsWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Problem loading EnrollmentDetailsWindow.fxml");
            e.printStackTrace();
        }

        EDWindowController dialogController = fxmlLoader.getController();
        dialogController.showEnrollment(student);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        // Custom button
        ButtonType reportButton = new ButtonType("Generate Report");
        dialog.getDialogPane().getButtonTypes().add(1, reportButton);

        // Setting default button
        Button defaultButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        defaultButton.setDefaultButton(true);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == dialog.getDialogPane().getButtonTypes().get(1)) {
            generateReportAlert(student);
        }
    }

    public void generateReport(Student student) {
        System.out.println("Generating report on" + student.getName());
        List<Subject> subjects = DbAccess.getInstance().studentEnrollment(student);

        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(new FileWriter("Report on " + student.getName()
                             + " " + LocalDate.now() + " .doc"))) {
            bufferedWriter.write("Student first name : " + student.getName());
            bufferedWriter.newLine();
            bufferedWriter.write("Student Last name : " + student.getLast_name());
            bufferedWriter.newLine();
            String[] array = DbAccess.getInstance().courseNameSQL(student).split("=");
            String course = array[0];
            bufferedWriter.write("Course name : " + course);
            bufferedWriter.newLine();
            bufferedWriter.write("Enrollment Date : " + subjects.get(0).getEnrollmentDate());
            bufferedWriter.newLine();
            bufferedWriter.write("Subjects: ");
            bufferedWriter.newLine();
            for (Subject s : subjects) {
                bufferedWriter.write("\t" + s.getSubject_name());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not generate report");
        }
    }

    public void generateReportAlert(Student student) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Generate Report");
        alert.setHeaderText("Generate report on : " + student.getName() + " " + student.getLast_name());
        alert.setContentText("Report will be saved in the project directory as \"Report on STUDENT NAME\".doc");

        alert.getButtonTypes().clear();

        // Creating custom button
        ButtonType proceedButton = new ButtonType("Proceed");
        alert.getButtonTypes().addAll(proceedButton, ButtonType.CANCEL);

        // Setting default button
        Button defaultButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        defaultButton.setDefaultButton(true);

        Optional<ButtonType> result = alert.showAndWait();
        if ((result.isPresent()) && (result.get() == proceedButton)) {
            generateReport(student);
        }
    }

    @FXML
    public void showCourseSubject() throws Exception {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindowID.getScene().getWindow());

        //To set the window non-modal; does not disable the main window
        dialog.initModality(Modality.NONE);
        dialog.setTitle("Course and Subjects");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/fxml/CourseSubjectsWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Problem loading CourseSubjectsWindow.fxml");
            e.printStackTrace();
        }

        CSWindowController csWindowController = fxmlLoader.getController();
        csWindowController.loadCourseSubjectUI();

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE) {
            dialog.close();
        }
    }

    @FXML
    public void showNewStudentWindow() throws SQLException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindowID.getScene().getWindow());
        dialog.initModality(Modality.NONE);
        dialog.setTitle("Add New Student");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/fxml/NewStudentWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Problem loading NewStudentWindow.fxml");
            e.printStackTrace();
        }

        ButtonType proceedButton = new ButtonType("Proceed");
        dialog.getDialogPane().getButtonTypes().addAll(proceedButton, ButtonType.CLOSE);
        Button button = (Button) dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        button.setDefaultButton(true);

        NewStudentWindowController controller = fxmlLoader.getController();
        Button proceed = (Button) dialog.getDialogPane().lookupButton(proceedButton);

        // To check if all fields are filled, this timer task refreshes every second
        // and decides whether to enable or disable the button

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                proceed.setDisable(controller.checkFields());
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(timerTask, 100, 1000);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == proceedButton){
            controller.collectData();
        }
        timer.cancel();
    }

    public void deleteStudent(Student student) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Student");
        alert.setHeaderText("Delete student: " +student.getName());
        alert.setContentText("Are you sure to delete this student?");

        Optional<ButtonType> result = alert.showAndWait();
        if((result.isPresent()) && (result.get() == ButtonType.OK)){
            DbAccess.getInstance().deleteStudent(student);
        }
    }

}

// This class copies the List of students to FXCollections.observableArrayList()
// This is done to see the progressbar in main UI, without this the upload is extremely fast
class LoadStudents extends Task<ObservableList<Student>> {
    @Override
    protected ObservableList<Student> call() throws InterruptedException {
        List<Student> studentList = DbAccess.getInstance().listStudents();
        ObservableList<Student> updatedStudentList = FXCollections.observableArrayList();
        for (int i = 0; i < studentList.size(); i++) {
            updatedStudentList.add(studentList.get(i));
            updateProgress(i + 1, studentList.size());
            Thread.sleep(100);
            // Thread.sleep to stimulate update and to see the progressbar in the main UI
        }
        return updatedStudentList;
    }
}



