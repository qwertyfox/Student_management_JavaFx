module Student.UI.and.Database {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens sample;
    opens sample.dao;
    opens sample.controller;
    opens sample.fxml;
    opens sample.model;
}