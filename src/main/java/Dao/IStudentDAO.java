package Dao;

import Models.Student;

import java.util.List;

public interface IStudentDAO extends IGenaricDAO<Student> {
    public List<Student> searchList(String search) throws DAOException;
}
