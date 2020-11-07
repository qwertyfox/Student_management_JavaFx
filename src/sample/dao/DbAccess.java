package sample.dao;

import sample.model.CourseSubjects;
import sample.model.Student;
import sample.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAccess {


    private Connection connection;

    private PreparedStatement querySubjectDetails;
    private PreparedStatement queryNewStudent;
    private PreparedStatement queryCourseId;
    private PreparedStatement queryInsertIntoStudentCourses;
    private PreparedStatement queryDeleteStudentCourse;
    private PreparedStatement queryDeleteStudent;

    private static final DbAccess instance = new DbAccess();

    public DbAccess() {
    }

    public static DbAccess getInstance() {
        return instance;
    }

    public void connectDB(String CONNECTION) {
        try {
            connection = DriverManager.getConnection(CONNECTION);
            System.out.println("Connection successful");

            queryNewStudent = connection.prepareStatement(SQLStatements.INSERT_NEW_STUDENT, Statement.RETURN_GENERATED_KEYS);
            querySubjectDetails = connection.prepareStatement(SQLStatements.QUERY_FIND_SUBJECT_DETAILS);
            queryCourseId = connection.prepareStatement(SQLStatements.QUERY_FIND_COURSE_ID);
            queryInsertIntoStudentCourses = connection.prepareStatement(SQLStatements.INSERT_INTO_STUDENT_COURSES);
            queryDeleteStudentCourse = connection.prepareStatement(SQLStatements.DELETE_STUDENT_COURSE_RECORD);
            queryDeleteStudent = connection.prepareStatement(SQLStatements.DELETE_STUDENT_RECORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection Error");
        }
    }

    public void terminateConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (querySubjectDetails != null) {
                querySubjectDetails.close();
            }
            if (queryNewStudent != null) {
                queryNewStudent.close();
            }
            if (queryCourseId != null) {
                queryCourseId.close();
            }
            if (queryInsertIntoStudentCourses != null) {
                queryInsertIntoStudentCourses.close();
            }
            if (queryDeleteStudent != null){
                queryDeleteStudent.close();
            }
            if(queryDeleteStudentCourse != null){
                queryDeleteStudent.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> listStudents() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatements.QUERY_SELECT_EVERYTHING_FROM_STUDENT)) {

            List<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudent_id(resultSet.getInt(1));
                student.setName(resultSet.getString(2));
                student.setLast_name(resultSet.getString(3));
                student.setContact_number(resultSet.getInt(4));
                students.add(student);
            }
            return students;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Student searchStudentFirstName(String name) {
        String query = "SELECT * FROM Students Where first_name = " + "\"" + name + "\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Student student = new Student();
            student.setStudent_id(resultSet.getInt(1));
            student.setName(resultSet.getString(2));
            student.setLast_name(resultSet.getString(3));
            student.setContact_number(resultSet.getInt(4));
            return student;
        } catch (SQLException e) {
            System.out.println("There is no record of this name");
            return null;
        }
    }

    public Student searchStudentLastName(String name) {
        String query = "SELECT * FROM Students Where last_name = " + "\"" + name + "\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            Student student = new Student();
            student.setStudent_id(resultSet.getInt(1));
            student.setName(resultSet.getString(2));
            student.setLast_name(resultSet.getString(3));
            student.setContact_number(resultSet.getInt(4));
            return student;
        } catch (SQLException e) {
            System.out.println("There is no record of this name");
            return null;
        }
    }

    public List<Subject> studentEnrollment(Student student) {
        String query = SQLStatements.QUERY_ENROLLMENT_DETAILS_BY_FIRST_NAME + "'" + student.getName() + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            List<Subject> subjects = new ArrayList<>();

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setSubject_name(resultSet.getString(4));
                subject.setEnrollmentDate(resultSet.getString(6));
                subjects.add(subject);
            }

            return subjects;
        } catch (SQLException e) {
            System.out.println("Error in the Query");
            return null;
        }
    }

    public String courseNameSQL(Student student) {
        String sql = SQLStatements.QUERY_ENROLLMENT_DATE_AND_COURSE + "'" + student.getName() + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            String courseName = null;
            while (resultSet.next()) {
                courseName = resultSet.getString(1) + "=" + resultSet.getString(2);
            }
            return courseName;
        } catch (SQLException e) {
            System.out.println("Error in the Query");
            return null;
        }
    }

    public List<CourseSubjects> courseSubjects() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatements.QUERY_EVERYTHING_FROM_COURSE_SUBJECT)) {
            List<CourseSubjects> courseSubjects = new ArrayList<>();
            while (resultSet.next()) {
                CourseSubjects cs = new CourseSubjects();
                cs.setCourse_name(resultSet.getString(1));
                cs.setSubject_name(resultSet.getString(2));
                courseSubjects.add(cs);
            }
            return courseSubjects;

        } catch (SQLException e) {
            System.out.println("Error in the Query");
            e.printStackTrace();
            return null;
        }
    }

    public String searchSubject(String subjectName) {
        try {
            querySubjectDetails.setString(1, subjectName);
            ResultSet resultSet = querySubjectDetails.executeQuery();
            String data = null;
            if (resultSet.next()) {
                data = resultSet.getString(1) + "=" + resultSet.getString(2);
            }
            return data;
        } catch (SQLException e) {
            System.out.println("Error in the Query");
            e.printStackTrace();
            return null;
        }
    }

    public void newStudent(String data) {
        try {
            connection.setAutoCommit(false);
            String[] array = data.split("=");
            String firstName = array[0];
            String last_name = array[1];
            String contact = array[2];
            String course = array[3];
            String dateOfEnrollment = array[4];

            queryNewStudent.setString(1, firstName);
            queryNewStudent.setString(2, last_name);
            queryNewStudent.setString(3, contact);

            int rowsAffected = queryNewStudent.executeUpdate();
            if (rowsAffected == 1) {
                connection.commit();
            } else {
                throw new SQLException("Error adding new Student");
            }

            ResultSet newStudentKey = queryNewStudent.getGeneratedKeys();
            if (newStudentKey.next()) {
                int studentKey = newStudentKey.getInt(1);
                enrollIntoCourse(studentKey, course, dateOfEnrollment);
            } else {
                throw new SQLException("Couldn't get the key for new student");
            }

        } catch (Exception e) {
            System.out.println("Insert Student failed " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Could not reset autocommit " + e.getMessage());
            }
        }
    }

    private void enrollIntoCourse(int studentKey, String courseName, String dateOfEnrollment) throws SQLException {

        queryCourseId.setString(1, courseName);
        ResultSet resultSet = queryCourseId.executeQuery();
        int courseKey = resultSet.getInt(1);

        queryInsertIntoStudentCourses.setInt(1, studentKey);
        queryInsertIntoStudentCourses.setInt(2, courseKey);
        queryInsertIntoStudentCourses.setString(3,dateOfEnrollment);

        int affectedRows = queryInsertIntoStudentCourses.executeUpdate();
        if(affectedRows != 1){
            System.out.println("Could not enroll the student");
        }
    }

    public void deleteStudent (Student student) throws SQLException {
        queryDeleteStudentCourse.setInt(1, student.getStudent_id());
        int affectedRows = queryDeleteStudentCourse.executeUpdate();
        if(affectedRows != 1){
            System.out.println("Error deleting the enrollment record");
        }

        queryDeleteStudent.setInt(1, student.getStudent_id());
        int affectedRows2 = queryDeleteStudent.executeUpdate();
        if(affectedRows2 != 1){
            System.out.println("Deleting student failed");
        }


    }

}
