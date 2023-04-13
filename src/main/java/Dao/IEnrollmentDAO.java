package Dao;

import Models.Enrollment;

public interface IEnrollmentDAO extends IGenaricDAO<Enrollment> {
    public Enrollment findEnrollment(String idCourse, String idStudent) throws DAOException;
    public void deleteEnrollment(String idCourse, String idStudent) throws DAOException;
}
