package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Subject {
    private SimpleIntegerProperty subject_id;
    private SimpleIntegerProperty course_id;
    private SimpleStringProperty subject_name;
    private String enrollmentDate;

    public Subject() {
        this.subject_id = new SimpleIntegerProperty();
        this.course_id = new SimpleIntegerProperty();
        this.subject_name = new SimpleStringProperty();
        this.enrollmentDate = "";
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public int getSubject_id() {
        return subject_id.get();
    }

    public SimpleIntegerProperty subject_idProperty() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id.set(subject_id);
    }

    public int getCourse_id() {
        return course_id.get();
    }

    public SimpleIntegerProperty course_idProperty() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id.set(course_id);
    }

    public String getSubject_name() {
        return subject_name.get();
    }

    public SimpleStringProperty subject_nameProperty() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name.set(subject_name);
    }
}
