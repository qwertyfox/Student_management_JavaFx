package sample.dao;

public class SQLStatements {

    //find
    public static final String QUERY_FIND_SUBJECT_DETAILS = "SELECT course_name, subject_name FROM Course_Subjects WHERE subject_name = ?";
    public static final String QUERY_FIND_COURSE_ID = "SELECT course_id FROM Courses WHERE course_name = ?";

    //Select everything
    public static final String QUERY_SELECT_EVERYTHING_FROM_STUDENT = "SELECT * FROM Students ORDER BY student_id";
    public static final String QUERY_EVERYTHING_FROM_COURSE_SUBJECT = "SELECT * FROM Course_Subjects";

    //Enrollments
    public static final String QUERY_ENROLLMENT_DETAILS_BY_FIRST_NAME = "SELECT * FROM Student_Enrollment WHERE first_name = ";
    public static final String QUERY_ENROLLMENT_DATE_AND_COURSE = "SELECT course_name, enrollment_date FROM Student_Enrollment WHERE first_name = ";

    //Insertions
    public static final String INSERT_NEW_STUDENT = "INSERT INTO Students (first_name, last_name, contact_number) VALUES (?, ?, ?)";
    public static final String INSERT_INTO_STUDENT_COURSES = "INSERT INTO Student_Courses (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";

    //Deletions
    public static final String DELETE_STUDENT_COURSE_RECORD = "DELETE FROM Student_Courses WHERE student_id = ?";
    public static final String DELETE_STUDENT_RECORD = "DELETE FROM Students WHERE student_id = ?";


}

//UPDATE Students SET last_name = 'Sah' WHERE first_name = 'Niraj'