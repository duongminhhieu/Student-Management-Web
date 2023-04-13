package Controllers;

import Dao.DAOFactory;
import Dao.IEnrollmentDAO;
import Dao.IStudentDAO;
import Models.Enrollment;
import Models.Student;
import Models.StudentOfCourse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/list-student-of-course")
public class ViewListStudentOfCourse extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        // Obtain UserDAO.
        IStudentDAO studentDAO = javabase.getStudentDAO();
        IEnrollmentDAO iEnrollmentDAO = javabase.getEnrollmentDAO();


        request.setCharacterEncoding("UTF-8");
        String search = request.getParameter("search");
        String sortSelected = request.getParameter("selected");
        String idCourse = request.getParameter("idCourse");
        List<StudentOfCourse> studentOfCourses = iEnrollmentDAO.lstStudentOfCourse(idCourse);

        request.setAttribute("title", "List Student Of Course: " + idCourse);

        if(sortSelected != null){
            studentOfCourses = studentDAO.sortListStudentOfCourse(studentOfCourses, sortSelected);
        }

        request.setAttribute("lstStudentOfCourse", studentOfCourses);

        request.setAttribute("func", "listStudentOfCourse");
        RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
        rd.forward(request, response);
    }


}
