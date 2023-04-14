package Dao.implementsDAO;

import Dao.DAOException;
import Dao.DAOFactory;
import Dao.IEnrollmentDAO;
import Dao.IStudentDAO;
import Models.Course;
import Models.Enrollment;
import Models.Student;
import Models.StudentOfCourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Dao.DAOUtil.prepareStatement;
import static Dao.DAOUtil.toSqlDate;

public class EnrollmentDAO implements IEnrollmentDAO {


    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_FIND_BY_ID =
            "SELECT idStudent, idCourse, score, enrollment_date FROM Enrollment WHERE idStudent = ? and idCourse = ?";

    private static final String SQL_LIST =
            "SELECT idStudent, idCourse, score, enrollment_date FROM Enrollment";

    private static final String SQL_CHECK_EXIST =
            "select COUNT(*) as amount from Enrollment where idStudent = ? and idCourse = ?";
    private static final String SQL_COUNT_STUDENT_OF_COURSE =
        "select count( distinct idStudent) as amount from Enrollment where idCourse = ?";
    private static final String SQL_INSERT =
            "INSERT INTO Enrollment (idStudent, idCourse, score, enrollment_date) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE Enrollment SET score = ?, enrollment_date = ? WHERE idStudent = ? and idCourse = ?";

    private static final String SQL_UPDATE_SCORE =
            "UPDATE Enrollment SET score = ? WHERE idStudent = ? and idCourse = ?";
    private static final String SQL_DELETE =
            "DELETE FROM Enrollment WHERE idStudent = ? and idCourse = ?";
    private static final String SQL_DELETE_COURSE =
            "DELETE FROM Enrollment WHERE idCourse = ?";

    private static final String SQL_DELETE_STUDENT =
            "DELETE FROM Enrollment WHERE idStudent = ?";
    private static final String SQL_LIST_STUDENT_OF_COURSE =
            "SELECT idStudent, idCourse, score, enrollment_date FROM Enrollment where idCourse = ?";

    private static final String SQL_LIST_ID_COURSE_OF_STUDENT =
            "select idCourse from Enrollment where idStudent = ?";

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

        if(checkExistEnrollment(enrollment.getCourseID(), enrollment.getStudentID()) != 0) return;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private int checkExistEnrollment(String idCourse, String idStudent){
        int check = 0 ;
        Object[] values = {
               idStudent,
                idCourse
        };
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_CHECK_EXIST, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                check = resultSet.getInt("amount");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return check;
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

    @Override
    public int countStudentOfCourse(String idCourse) throws DAOException {
        int count = 0 ;
        Object[] values = {
                idCourse,
        };
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_COUNT_STUDENT_OF_COURSE, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                count = resultSet.getInt("amount");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return count;
    }

    @Override
    public void deleteCourse(String idCourse) throws DAOException {
        Object[] values = {
                idCourse
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE_COURSE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                //user.setId(null);
                idCourse = null;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteStudent(String idStudent) throws DAOException {
        Object[] values = {
                idStudent
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE_STUDENT, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                //user.setId(null);
                idStudent = null;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public List<String> lstIDCourseOfStudent(String idStudent) throws DAOException {
        List<String> list = new ArrayList<>();
        Object[] values = {
                idStudent
        };
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_LIST_ID_COURSE_OF_STUDENT, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                list.add(resultSet.getString("idCourse"));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return list;
    }

    @Override
    public void updateScore(String idStudent, String idCourse, float score) {

        Object[] values = {
                score,
                idStudent,
                idCourse
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE_SCORE, false, values);
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
    public List<StudentOfCourse> lstStudentOfCourse(String idCourse) throws DAOException {
        List<StudentOfCourse> studentOfCourses = new ArrayList<>();
        Object[] values = {
                idCourse,
        };
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_LIST_STUDENT_OF_COURSE, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            IStudentDAO iStudentDAO = daoFactory.getStudentDAO();
            List<Student> st = iStudentDAO.list();
            while (resultSet.next()) {
                studentOfCourses.add(mapStudentOfCourse(resultSet, st));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return studentOfCourses;
    }

    private static Enrollment map(ResultSet resultSet) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentID(resultSet.getString("idStudent"));
        enrollment.setCourseID(resultSet.getString("idCourse"));
        enrollment.setScore(resultSet.getFloat("score"));
        enrollment.setEnrollmentDate(resultSet.getDate("enrollment_date"));
        return enrollment;
    }

    private static StudentOfCourse mapStudentOfCourse(ResultSet resultSet, List<Student> lstSt) throws SQLException {
        StudentOfCourse st = new StudentOfCourse();
        st.setId(resultSet.getString("idStudent"));
        st.setIdCourse(resultSet.getString("idCourse"));
        st.setScore(resultSet.getFloat("score"));
        st.setEnrollmentDate(resultSet.getDate("enrollment_date"));

        for (Student p : lstSt) {
            if (p.getId().equals(st.getId())) {
                st.setName(p.getName());
                st.setGrade(p.getGrade());
                st.setBirthday(p.getBirthday());
                st.setNotes(p.getNotes());
                st.setAddress(p.getAddress());
                break;
            }
        }

        return st;
    }

}
