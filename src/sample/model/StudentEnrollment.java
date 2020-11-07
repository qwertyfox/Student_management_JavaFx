package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentEnrollment {
    private SimpleIntegerProperty student_id;
    private SimpleStringProperty first_name;
    private SimpleStringProperty last_name;
    private SimpleStringProperty course_name;
    private SimpleStringProperty subject_name;

    public StudentEnrollment(){
        this.student_id = new SimpleIntegerProperty();
        this.first_name = new SimpleStringProperty();
        this.last_name = new SimpleStringProperty();
        this.course_name = new SimpleStringProperty();
        this.subject_name = new SimpleStringProperty();
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

    public int getStudent_id() {
        return student_id.get();
    }

    public SimpleIntegerProperty student_idProperty() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id.set(student_id);
    }

    public String getFirst_name() {
        return first_name.get();
    }

    public SimpleStringProperty first_nameProperty() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
    }

    public String getLast_name() {
        return last_name.get();
    }

    public SimpleStringProperty last_nameProperty() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
    }

    public String getCourse_name() {
        return course_name.get();
    }

    public SimpleStringProperty course_nameProperty() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name.set(course_name);
    }
}
