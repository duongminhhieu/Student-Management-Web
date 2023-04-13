package Dao;

import Models.Course;
import Models.Student;
import Models.StudentOfCourse;

import java.util.List;

public interface IStudentDAO extends IGenaricDAO<Student> {
    public List<Student> searchList(String search) throws DAOException;
    public List<Student> sortList(List<Student> studentList, String selected) throws DAOException;
    public List<StudentOfCourse> sortListStudentOfCourse(List<StudentOfCourse> studentList, String selected) throws DAOException;

}
