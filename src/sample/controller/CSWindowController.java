package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.dao.DbAccess;
import sample.model.CourseSubjects;

public class CSWindowController {
    @FXML
    public TableView<CourseSubjects> cSTableViewId;
    @FXML
    public TextField searchFieldId;
    @FXML
    public TextArea textAreaId;



    public void loadCourseSubjectUI(){
        Task<ObservableList<CourseSubjects>> task = new Task<>() {
            @Override
            protected ObservableList<CourseSubjects> call() throws Exception {
                return FXCollections.observableArrayList(DbAccess.getInstance().courseSubjects());
            }
        };
        new Thread(task).start();
        cSTableViewId.itemsProperty().bind(task.valueProperty());
    }

    public void searchSubject(){
        String subjectName = searchFieldId.getText();
        String test = DbAccess.getInstance().searchSubject(subjectName);
        if(test == null){
            textAreaId.setText("The subject is not in the database");
        }else{
            String [] array = test.split("=");
            textAreaId.setText(array [0]+"\n"+ array [1]);
        }
    }
}
