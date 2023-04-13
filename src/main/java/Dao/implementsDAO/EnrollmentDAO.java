package Dao.implementsDAO;

import Dao.DAOException;
import Dao.DAOFactory;
import Dao.IEnrollmentDAO;
import Models.Course;
import Models.Enrollment;
import Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Dao.DAOUtil.prepareStatement;
import static Dao.DAOUtil.toSqlDate;

public class EnrollmentDAO implements IEnrollmentDAO {


    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_FIND_BY_ID =
            "SELECT idStudent, idCourse, score, enrollment_date FROM Enrollment WHERE idStudent = ? and idCourse = ?";

    private static final String SQL_LIST =
            "SELECT idStudent, idCourse, score, enrollment_date FROM Enrollment";

    private  static final String SQL_SEARCH_STUDENT_BY_NAME =
            "SELECT id, name, grade, birthday, address, note FROM Student WHERE name like ?";
    private static final String SQL_INSERT =
            "INSERT INTO Enrollment (idStudent, idCourse, score, enrollment_date) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE Enrollment SET score = ?, enrollment_date = ? WHERE idStudent = ? and idCourse = ?";
    private static final String SQL_DELETE =
            "DELETE FROM Enrollment WHERE idStudent = ? and idCourse = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    public EnrollmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Enrollment find(String id) throws DAOException {
        return null;
    }

    @Override
    public List<Enrollment> list() throws DAOException {
        List<Enrollment> enrollments = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                enrollments.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return enrollments;
    }

    @Override
    public void create(Enrollment enrollment) throws IllegalArgumentException, DAOException {
        Object[] values = {
                enrollment.getStudentID(),
                enrollment.getCourseID(),
                enrollment.getScore(),
                toSqlDate(enrollment.getEnrollmentDate()),
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }

//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    user.setId(String.valueOf(generatedKeys.getLong(1)));
//                } else {
//                    throw new DAOException("Creating user failed, no generated key obtained.");
//                }
//            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Enrollment enrollment) throws IllegalArgumentException, DAOException {
        if (enrollment.getStudentID() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
                enrollment.getScore(),
                toSqlDate(enrollment.getEnrollmentDate()),
                enrollment.getStudentID(),
                enrollment.getCourseID(),
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(String id) throws DAOException {
        return;
    }

    @Override
    public Enrollment findEnrollment(String idCourse, String idStudent) throws DAOException {
        Enrollment enrollment = new Enrollment();

        Object[] values = {
                idStudent,
                idCourse
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_FIND_BY_ID, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                enrollment = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return enrollment;
    }

    @Override
    public void deleteEnrollment(String idCourse, String idStudent) throws DAOException {
        Object[] values = {
                idStudent,
                idCourse
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                //user.setId(null);
                idCourse = null;
                idStudent = null;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static Enrollment map(ResultSet resultSet) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentID(resultSet.getString("idStudent"));
        enrollment.setCourseID(resultSet.getString("idCourse"));
        enrollment.setScore(resultSet.getFloat("score"));
        enrollment.setEnrollmentDate(resultSet.getDate("enrollment_date"));
        return enrollment;
    }
}
