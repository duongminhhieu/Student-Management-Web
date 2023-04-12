package Dao.implementsDAO;

import Dao.DAOException;
import Dao.DAOFactory;
import Dao.ICourseDAO;
import Models.Course;
import Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Dao.DAOUtil.prepareStatement;
import static Dao.DAOUtil.toSqlDate;

public class CourseDAO implements ICourseDAO {

    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_FIND_BY_ID =
            "SELECT id, name, lecture, year, note FROM Course WHERE id = ?";

    private static final String SQL_SEARCH_COURSE_BY_NAME =
            "SELECT id, name, lecture, year, note FROM Course WHERE name like ?";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT id, name, lecture, year, note FROM Course ORDER BY id";
    private static final String SQL_INSERT =
            "INSERT INTO Course (id, name, lecture, year, note) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE Course SET name = ?, lecture = ?, year = ?, note = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM Course WHERE id = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    public CourseDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public Course find(String id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    @Override
    public List<Course> list() throws DAOException {
        List<Course> courses = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                courses.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return courses;
    }

    @Override
    public void create(Course course) throws IllegalArgumentException, DAOException {
        Object[] values = {
                course.getId(),
                course.getName(),
                course.getLecture(),
                course.getYear(),
                course.getNotes(),
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
    public void update(Course course) throws IllegalArgumentException, DAOException {
        if (course.getId() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
                course.getName(),
                course.getLecture(),
                course.getYear(),
                course.getNotes(),
                course.getId()
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
    public void delete(String courseID) throws DAOException {
        Object[] values = {
                courseID
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                //course.setId(null);
                courseID = null;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    // method
    private Course find(String sql, Object... values) throws DAOException {
        Course course = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                course = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return course;
    }

    private static Course map(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getString("id"));
        course.setName(resultSet.getString("name"));
        course.setLecture(resultSet.getString("lecture"));
        course.setYear(resultSet.getInt("year"));
        course.setNotes(resultSet.getString("note"));
        return course;
    }

    @Override
    public List<Course> searchList(String search) throws DAOException {
        List<Course> courses = new ArrayList<>();
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_SEARCH_COURSE_BY_NAME, false, "%" + search + "%");

                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                courses.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return courses;
    }
}
