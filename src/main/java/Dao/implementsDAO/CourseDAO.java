package Dao.implementsDAO;

import Dao.DAOException;
import Dao.DAOFactory;
import Dao.ICourseDAO;
import Dao.IEnrollmentDAO;
import Models.Course;
import Models.Enrollment;
import Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.*;

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
    private static final String SQL_CHECK_EXIST =
            "select COUNT(*) as amount from Course where id = ?";
    private static final String SQL_LIST_COURSE_OF_STUDENT =
            "select COUNT(*) as amount from Course where id = ?";

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
        if(checkExistCourse(course.getId()) != 0) return;
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


    private int checkExistCourse(String idCourse){
        int checkExist = 0;
        Object[] values = {
                idCourse,
        };
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_CHECK_EXIST, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                checkExist = resultSet.getInt("amount");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return checkExist;
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
        IEnrollmentDAO enrollmentDAO = daoFactory.getEnrollmentDAO();
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            enrollmentDAO.deleteCourse(courseID);
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

    @Override
    public List<Course> sortList(List<Course> courseList, String selected) throws DAOException {
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        if (Objects.equals(selected, "1")){
            collator.setStrength(Collator.SECONDARY); // Không phân biệt chữ hoa, chữ thường,
            // Sắp xếp List theo thứ tự từ A-Z của key
            Collections.sort(courseList, new Comparator<Course>() {
                public int compare(Course o1, Course o2) {
                    return collator.compare(o1.getName(), o2.getName());
                }
            });
        } else if(Objects.equals(selected, "2")){
            collator.setStrength(Collator.SECONDARY); // Không phân biệt chữ hoa, chữ thường,
            // Sắp xếp List theo thứ tự từ Z - A của key
            Collections.sort(courseList, new Comparator<Course>() {
                public int compare(Course o1, Course o2) {
                    return collator.compare(o2.getName(), o1.getName());
                }
            });
        }

        return courseList;
    }

    @Override
    public List<Course> listCourseOfStudent(String idStudent) throws DAOException {
        List<Course> courses = new ArrayList<>();

        IEnrollmentDAO enrollmentDAO = daoFactory.getEnrollmentDAO();

        List<String> list = enrollmentDAO.lstIDCourseOfStudent(idStudent);

        for(String id: list){
            System.out.println(id);
            Course course = find(id);
            courses.add(course);
        }

        return courses;
    }

    @Override
    public List<Course> listCourseOfStudentByYear(String idStudent, int year) throws DAOException {
        List<Course> courses = new ArrayList<>();

        IEnrollmentDAO enrollmentDAO = daoFactory.getEnrollmentDAO();

        List<String> list = enrollmentDAO.lstIDCourseOfStudent(idStudent);

        for(String id: list){
            System.out.println(id);
            Course course = find(id);
            if(year == course.getYear())
                courses.add(course);
        }

        return courses;
    }
}
