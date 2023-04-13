package Controllers;

import Dao.DAOFactory;
import Dao.ICourseDAO;
import Dao.IEnrollmentDAO;
import Dao.IStudentDAO;
import Models.Course;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/list-course")
public class ListCourseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        // Obtain UserDAO.
        ICourseDAO courseDAO = javabase.getCourseDAO();
        IEnrollmentDAO iEnrollmentDAO = javabase.getEnrollmentDAO();
        request.setCharacterEncoding("UTF-8");

        String search = request.getParameter("search");
        String sortSelected = request.getParameter("selected");

        List<Course> lstCourse = new ArrayList<Course>();
        if(search == null){
            lstCourse = courseDAO.list();
            for(int i = 0; i <lstCourse.size(); i ++){
                int amount = iEnrollmentDAO.countStudentOfCourse(lstCourse.get(i).getId());
                lstCourse.get(i).setAmountStudent(amount);
            }
            request.setAttribute("title", "List Course");
        } else {
            lstCourse = courseDAO.searchList(search);
            for(int i = 0; i <lstCourse.size(); i ++){
                int amount = iEnrollmentDAO.countStudentOfCourse(lstCourse.get(i).getId());
                lstCourse.get(i).setAmountStudent(amount);
            }
            request.setAttribute("title", "Searching for: " + search);
        }

        if(sortSelected != null){
            lstCourse = courseDAO.sortList(lstCourse, sortSelected);
        }


        request.setAttribute("lstCourse", lstCourse);
        request.setAttribute("func", "listCourse");
        RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String data = reader.readLine();

        response.setContentType("text/plain");
        response.getWriter().write(data);
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
