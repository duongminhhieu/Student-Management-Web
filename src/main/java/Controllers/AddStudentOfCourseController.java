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
import java.util.ArrayList;
import java.util.List;


@WebServlet("/add-student-of-course")
public class AddStudentOfCourseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        // Obtain UserDAO.
        IStudentDAO studentDAO = javabase.getStudentDAO();
        request.setCharacterEncoding("UTF-8");
        String search = request.getParameter("search");
        String sortSelected = request.getParameter("selected");

        List<Student> lstStudent = new ArrayList<Student>();
        if(search == null){
            lstStudent = studentDAO.list();
            request.setAttribute("title", "List Student");
        } else {
            lstStudent = studentDAO.searchList(search);
            request.setAttribute("title", "Searching for: " + search);
        }

        if(sortSelected != null){
            lstStudent = studentDAO.sortList(lstStudent, sortSelected);
        }

        request.setAttribute("listStudent", lstStudent);

        request.setAttribute("func", "listStudent");
        RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
        rd.forward(request, response);
    }
}
