package sample.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.dao.DbAccess;

import java.io.File;
import java.io.IOException;

public class DBLocatorController {
    @FXML
    public AnchorPane anchorPaneId;
    @FXML
    public Label labelId;


    public void locateDb() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Database");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("SQLite Database", "*.db"));

        File file = chooser.showOpenDialog(anchorPaneId.getScene().getWindow());
        String loc;

        if (file != null) {
            loc = "jdbc:sqlite:" + file.getAbsolutePath();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/fxml/MainWindow.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            primaryStage.setTitle("Student Database");
            primaryStage.setScene(new Scene(root, 800, 500));
            primaryStage.show();
            DbAccess.getInstance().connectDB(loc);
            controller.loadMainUI();
            anchorPaneId.getScene().getWindow().hide();

        } else {
            labelId.setVisible(true);
        }
    }
}
