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
import java.util.Objects;

@WebServlet("/edit-student")
public class EditStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");

        // Obtain UserDAO.
        IStudentDAO studentDAO = javabase.getStudentDAO();
        String idStudent = request.getParameter("idStudent");

        if( idStudent == null){
            request.setAttribute("func", "404");

            RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
            rd.forward(request, response);
        } else {
            Student student = studentDAO.find(idStudent);

            request.setAttribute("student", student);
            request.setAttribute("func", "editStudent");
            request.setAttribute("title", "Edit Student: " + idStudent);


            RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
            rd.forward(request, response);
        }
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
        float grade = !Objects.equals(request.getParameter("grade"), "") ? Float.parseFloat(request.getParameter("grade")):0;
        String dateString = request.getParameter("birthday");
        String address = request.getParameter("address");
        String notes = request.getParameter("note");

        System.out.println(id +  name + grade + dateString + address + notes);
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

        studentDAO.update(student);
        System.out.println("Student successfully updated: " + student);
        response.sendRedirect("/list-student");
    }
}
