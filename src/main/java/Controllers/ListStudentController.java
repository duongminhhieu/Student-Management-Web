package Controllers;

import Dao.DAOFactory;
import Dao.IStudentDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/list-student")
public class ListStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");

        // Obtain UserDAO.
        IStudentDAO studentDAO = javabase.getStudentDAO();

        request.setAttribute("func", "listStudent");

        request.setAttribute("lstSt", studentDAO.list());
        RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
        rd.forward(request, response);

    }
}
