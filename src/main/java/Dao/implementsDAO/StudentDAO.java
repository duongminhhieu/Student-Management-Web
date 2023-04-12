package Dao.implementsDAO;

import Dao.DAOException;
import Dao.DAOFactory;
import Dao.IStudentDAO;
import Models.Course;
import Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.*;

import static Dao.DAOUtil.prepareStatement;
import static Dao.DAOUtil.toSqlDate;

public class StudentDAO implements IStudentDAO {


    // Constants ----------------------------------------------------------------------------------
    private static final String SQL_FIND_BY_ID =
            "SELECT id, name, grade, birthday, address, note FROM Student WHERE id = ?";

    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT id, name, grade, birthday, address, note FROM Student ORDER BY id";

    private  static final String SQL_SEARCH_STUDENT_BY_NAME =
            "SELECT id, name, grade, birthday, address, note FROM Student WHERE name like ?";
    private static final String SQL_INSERT =
            "INSERT INTO Student (id, name, grade, birthday, address, note) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE Student SET name = ?, grade = ?, birthday = ?, address = ?, note = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM Student WHERE id = ?";

    // Vars ---------------------------------------------------------------------------------------
    private DAOFactory daoFactory;

    public StudentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public Student find(String id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    @Override
    public List<Student> list() throws DAOException {
        List<Student> users = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return users;
    }

    @Override
    public void create(Student user) throws IllegalArgumentException, DAOException {


        Object[] values = {
                user.getId(),
                user.getName(),
                user.getGrade(),
                toSqlDate(user.getBirthday()),
                user.getAddress(),
                user.getNotes(),
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
    public void update(Student user) throws IllegalArgumentException, DAOException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
                user.getName(),
                user.getGrade(),
                toSqlDate(user.getBirthday()),
                user.getAddress(),
                user.getNotes(),
                user.getId()
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
        Object[] values = {
                id
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
                id = null;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }



    // Method
    private Student find(String sql, Object... values) throws DAOException {
        Student user = null;

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                user = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return user;
    }

    private static Student map(ResultSet resultSet) throws SQLException {
        Student user = new Student();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setGrade(resultSet.getFloat("grade"));
        user.setBirthday(resultSet.getDate("birthday"));
        user.setAddress(resultSet.getString("address"));
        user.setNotes(resultSet.getString("note"));
        return user;
    }

    @Override
    public List<Student> searchList(String search) throws DAOException {
        List<Student> users = new ArrayList<>();
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_SEARCH_STUDENT_BY_NAME, false, "%" + search + "%");

                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return users;
    }

    @Override
    public List<Student> sortList(List<Student> studentList, String selected) throws DAOException {
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        if (Objects.equals(selected, "1")){
            collator.setStrength(Collator.SECONDARY); // Không phân biệt chữ hoa, chữ thường,
            // Sắp xếp List theo thứ tự từ A-Z của key
            Collections.sort(studentList, new Comparator<Student>() {
                public int compare(Student o1, Student o2) {
                    return collator.compare(o1.getName(), o2.getName());
                }
            });
        } else if(Objects.equals(selected, "2")){
            collator.setStrength(Collator.SECONDARY); // Không phân biệt chữ hoa, chữ thường,
            // Sắp xếp List theo thứ tự từ Z - A của key
            Collections.sort(studentList, new Comparator<Student>() {
                public int compare(Student o1, Student o2) {
                    return collator.compare(o2.getName(), o1.getName());
                }
            });
        } else if(Objects.equals(selected, "3")){
            // Sắp xếp List theo thứ tự từ ASC
            Collections.sort(studentList, new Comparator<Student>() {
                public int compare(Student o1, Student o2) {
                    return Float.compare(o1.getGrade(), o2.getGrade());
                }
            });
        } else if(Objects.equals(selected, "4")){
            // Sắp xếp List theo thứ tự từ DESC
            Collections.sort(studentList, new Comparator<Student>() {
                public int compare(Student o1, Student o2) {
                    return Float.compare(o2.getGrade(), o1.getGrade());
                }
            });
        }


        return studentList;
    }
}
