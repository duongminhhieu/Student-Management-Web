package Controllers;

import Dao.DAOFactory;
import Dao.ICourseDAO;
import Dao.IStudentDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/list-course")
public class ListCourseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        // Obtain UserDAO.
        ICourseDAO courseDAO = javabase.getCourseDAO();
        request.setCharacterEncoding("UTF-8");

        String search = request.getParameter("search");
        if(search == null){
            request.setAttribute("lstCourse", courseDAO.list());
            request.setAttribute("title", "List Course");
        } else {
            request.setAttribute("lstCourse", courseDAO.searchList(search));
            request.setAttribute("title", "Searching for: " + search);
        }
        request.setAttribute("func", "listCourse");


        RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
        rd.forward(request, response);

    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String courseID = reader.readLine();
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");

        // Obtain UserDAO.
        ICourseDAO courseDAO = javabase.getCourseDAO();
        courseDAO.delete(courseID);

    }
}
