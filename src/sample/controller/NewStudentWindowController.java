package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import sample.dao.DbAccess;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NewStudentWindowController {
    @FXML
    public DatePicker datePickerId;
    @FXML
    public TextField firstNameId;
    @FXML
    public TextField contactFieldId;
    @FXML
    public TextField lastNameFieldId;
    @FXML
    public MenuButton menuButtonId;
    @FXML
    public MenuItem BAid;
    @FXML
    public MenuItem BITid;
    @FXML
    public MenuItem MAid;
    @FXML
    public MenuItem MITid;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    public void initialize(){

    }

    public void collectData() throws SQLException {
        String firstName = firstNameId.getText();
        String lastName = lastNameFieldId.getText();
        String contact = contactFieldId.getText();
        String course = menuButtonId.getText();
        LocalDate date = datePickerId.getValue();


        String data = firstName + "=" + lastName + "=" + contact + "=" + course + "=" + formatter.format(date);
        // The "=" is later split and saved into an array to get meaningful data

        DbAccess.getInstance().newStudent(data);
    }

    public boolean checkFields() {
        String firstName = firstNameId.getText();
        String lastName = lastNameFieldId.getText();
        String contact = contactFieldId.getText();
        String course = menuButtonId.getText();
        LocalDate date = datePickerId.getValue();


        // The following are the conditions to be checked before the insert statement is committed
        // "Proceed" button is disabled until all information are filled

        return firstName.isEmpty() || firstName.trim().isEmpty()
                || lastName.isEmpty() || lastName.trim().isEmpty()
                || contact.isEmpty() || contact.trim().isEmpty()
                || course.isEmpty() || course.trim().isEmpty()
                || date == null;
    }

    public void BAidSelected() {
         menuButtonId.setText(BAid.getText());
    }
    public void MAidSelected() {
        menuButtonId.setText(MAid.getText());
    }
    public void BITidSelected() {
        menuButtonId.setText( BITid.getText());
    }
    public void MITidSelected() {
        menuButtonId.setText(MITid.getText());
    }

    public void proceed(){

    }

}
