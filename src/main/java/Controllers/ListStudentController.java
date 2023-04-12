package Controllers;

import Dao.DAOFactory;
import Dao.IStudentDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/list-student")
public class ListStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        // Obtain UserDAO.
        IStudentDAO studentDAO = javabase.getStudentDAO();
        request.setCharacterEncoding("UTF-8");
        String search = request.getParameter("search");
        if(search == null){
            request.setAttribute("lstSt", studentDAO.list());
            request.setAttribute("title", "List Student");
        } else {
            request.setAttribute("lstSt", studentDAO.searchList(search));
            request.setAttribute("title", "Searching for: " + search);
        }
        request.setAttribute("func", "listStudent");


        RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
        rd.forward(request, response);
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String studentID = reader.readLine();
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");

        // Obtain UserDAO.
        IStudentDAO studentDAO = javabase.getStudentDAO();
        studentDAO.delete(studentID);

    }
}
