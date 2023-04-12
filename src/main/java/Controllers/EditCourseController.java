package Controllers;

import Dao.DAOFactory;
import Dao.ICourseDAO;
import Dao.IStudentDAO;
import Models.Course;
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

@WebServlet("/edit-course")
public class EditCourseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");

        // Obtain UserDAO.
        ICourseDAO courseDAO = javabase.getCourseDAO();
        String idCourse = request.getParameter("idCourse");

        if( idCourse == null){
            request.setAttribute("func", "404");

            RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
            rd.forward(request, response);
        } else {
            Course course = courseDAO.find(idCourse);

            request.setAttribute("course", course);
            request.setAttribute("func", "editCourse");
            request.setAttribute("title", "Edit Course: " + idCourse);


            RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtain DAOFactory.
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");

        // Obtain UserDAO.
        ICourseDAO courseDAO = javabase.getCourseDAO();
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String lecture = request.getParameter("lecture");
        int year = Integer.parseInt(request.getParameter("year"));
        String notes = request.getParameter("note");

        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setLecture(lecture);
        course.setYear(year);
        course.setNotes(notes);

        courseDAO.update(course);
        System.out.println("Course successfully updated: " + course);
        response.sendRedirect("/list-course");
    }

}
