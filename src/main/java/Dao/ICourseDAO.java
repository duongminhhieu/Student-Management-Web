package Dao;

import Models.Course;
import Models.Student;

import java.util.List;

public interface ICourseDAO extends IGenaricDAO<Course> {
    public List<Course> searchList(String search) throws DAOException;
    public List<Course> sortList(List<Course> courseList, String selected) throws DAOException;
    public List<Course> listCourseOfStudent(String idStudent) throws DAOException;
    public List<Course> listCourseOfStudentByYear(String idStudent, int year) throws DAOException;
}
