package Dao;

import Models.Enrollment;
import Models.Student;
import Models.StudentOfCourse;

import java.util.List;

public interface IEnrollmentDAO extends IGenaricDAO<Enrollment> {
    public Enrollment findEnrollment(String idCourse, String idStudent) throws DAOException;
    public void deleteEnrollment(String idCourse, String idStudent) throws DAOException;
    public int countStudentOfCourse(String idCourse) throws  DAOException;
    public void deleteCourse(String idCourse) throws DAOException;
    public void deleteStudent(String idStudent) throws DAOException;

    public void updateScore(String idStudent, String idCourse, float score);
    public List<StudentOfCourse> lstStudentOfCourse(String idCourse) throws DAOException;
}
