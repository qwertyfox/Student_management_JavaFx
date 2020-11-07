package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
    private SimpleIntegerProperty student_id;
    private SimpleStringProperty name;
    private SimpleStringProperty last_name;
    private SimpleIntegerProperty contact_number;

    public Student() {
        this.student_id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.last_name = new SimpleStringProperty();
        this.contact_number = new SimpleIntegerProperty();
    }

    public int getStudent_id() {
        return student_id.get();
    }

    public void setStudent_id(int student_id) {
        this.student_id.set(student_id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLast_name() {
        return last_name.get();
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
    }

    public int getContact_number() {
        return contact_number.get();
    }

    public void setContact_number(int contact_number) {
        this.contact_number.set(contact_number);
    }
}
