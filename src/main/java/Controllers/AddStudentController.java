
package Controllers;

import Dao.DAOFactory;
import Dao.IStudentDAO;
import Models.Student;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/add-student")
public class AddStudentController extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("func", "addStudent");
        request.setAttribute("title", "Add Student");
        RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");

        // Obtain UserDAO.
        IStudentDAO studentDAO = javabase.getStudentDAO();

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        float grade = Float.parseFloat(request.getParameter("grade"));
        String dateString = request.getParameter("birthday");
        String address = request.getParameter("address");
        String notes = request.getParameter("note");

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setGrade(grade);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date chosenDate;
        try {
            chosenDate = inputFormat.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        student.setBirthday(chosenDate);
        student.setAddress(address);
        student.setNotes(notes);


        studentDAO.create(student);
        System.out.println("Student successfully created: " + student);


        response.sendRedirect("/list-student");
    }
}