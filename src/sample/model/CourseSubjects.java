package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class CourseSubjects {
    private SimpleStringProperty course_name;
    private SimpleStringProperty subject_name;

    public CourseSubjects() {
        this.course_name = new SimpleStringProperty();
        this.subject_name = new SimpleStringProperty();
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
