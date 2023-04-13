package Controllers;

import Dao.DAOFactory;
import Dao.IEnrollmentDAO;
import Dao.IStudentDAO;
import Dao.implementsDAO.EnrollmentDAO;
import Models.Enrollment;
import Models.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
            request.setAttribute("title", "Choose Student");
        } else {
            lstStudent = studentDAO.searchList(search);
            request.setAttribute("title", "ChooseStudent - Searching for: " + search);
        }

        if(sortSelected != null){
            lstStudent = studentDAO.sortList(lstStudent, sortSelected);
        }

        request.setAttribute("listAddStudentCourse", lstStudent);

        request.setAttribute("func", "listAddStudentCourse");
        RequestDispatcher rd = request.getRequestDispatcher("Views/Layouts/main.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String[] myArray = mapper.readValue(request.getReader(), String[].class);

        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        IEnrollmentDAO enrollmentDAO = javabase.getEnrollmentDAO();

        for (String s : myArray) {
            Enrollment enrollment = new Enrollment();
            enrollment.setCourseID(request.getParameter("idCourse"));
            enrollment.setStudentID(s);
            enrollment.setScore(0);
            enrollment.setEnrollmentDate(new Date());
            enrollmentDAO.create(enrollment);

        }



    }

}
