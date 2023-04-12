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

            RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
            rd.forward(request, response);
        }


    }
}
