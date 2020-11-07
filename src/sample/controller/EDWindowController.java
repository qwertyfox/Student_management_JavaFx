package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import sample.dao.DbAccess;
import sample.model.Student;
import sample.model.Subject;

public class EDWindowController {
    @FXML
    public Label nameLabel;
    @FXML
    public Label courseLabel;
    @FXML
    public TableView<Subject> tableViewId;
    @FXML
    public Label enrollmentLabel;


    public void showEnrollment(Student student){
        Task<ObservableList<Subject>> task = new Task<>() {
            @Override
            protected ObservableList<Subject> call() throws Exception {
                return FXCollections.observableArrayList(DbAccess.getInstance().studentEnrollment(student));
            }
        };
        new Thread(task).start();
        tableViewId.itemsProperty().bind(task.valueProperty());

        nameLabel.setFont(new Font("Arial bold",20));
        nameLabel.setText(student.getName() + " " +student.getLast_name());
        String [] array = DbAccess.getInstance().courseNameSQL(student).split("=");
        String course = array [0];
        String enrollmentDate = array [1];
        courseLabel.setFont(new Font("Times New Roman italic",12));
        courseLabel.setText(course);
        enrollmentLabel.setText("Enrolled on : "+ enrollmentDate);

    }

}
