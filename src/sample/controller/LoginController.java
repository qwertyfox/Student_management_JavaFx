package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    public PasswordField passwordFieldId;
    @FXML
    public TextField userNameId;
    @FXML
    public Button loginButton;
    @FXML
    public Label labelId;
    @FXML
    public Label warningLabelId;
    @FXML
    public AnchorPane loginWindowId;

    public void login(ActionEvent actionEvent) throws Exception {

        if(userNameId.getText().equals("Admin") && passwordFieldId.getText().equals("1234")){
            loginWindowId.getScene().getWindow().hide();
            System.out.println("Login successful");
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/DBLocatorWindow.fxml"));
            primaryStage.setScene(new Scene(root,200,100));
            primaryStage.show();
        }else {
            warningLabelId.setVisible(true);
            warningLabelId.setText("Login Failed!");
            warningLabelId.setTextFill(Color.RED);
        }
    }
}
