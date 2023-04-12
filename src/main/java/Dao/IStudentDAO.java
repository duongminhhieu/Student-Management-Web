package Dao;

import Models.Course;
import Models.Student;

import java.util.List;

public interface IStudentDAO extends IGenaricDAO<Student> {
    public List<Student> searchList(String search) throws DAOException;
    public List<Student> sortList(List<Student> studentList, String selected) throws DAOException;
}
