package Controllers;

import Dao.DAOFactory;
import Dao.ICourseDAO;
import Dao.IEnrollmentDAO;
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

@WebServlet("/list-course-in-the-year")
public class ListCourseInTheYearController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        // Obtain UserDAO.
        ICourseDAO courseDAO = javabase.getCourseDAO();
        IEnrollmentDAO iEnrollmentDAO = javabase.getEnrollmentDAO();
        request.setCharacterEncoding("UTF-8");

        String sortSelected = request.getParameter("selected");
        String idStudent = request.getParameter("idStudent");
        String yearCurrent = request.getParameter("year");


        List<Course> lstCourse = new ArrayList<Course>();

        if(yearCurrent != null && !yearCurrent.equals("all")){
            lstCourse = courseDAO.listCourseOfStudentByYear(idStudent, Integer.parseInt(yearCurrent));
        } else {
            lstCourse = courseDAO.listCourseOfStudent(idStudent);
        }
        // laasy so luong hs cua khoa hoc
        getAmountStudent(lstCourse, iEnrollmentDAO);
        request.setAttribute("title", "List Course");

        if (sortSelected != null) {
            lstCourse = courseDAO.sortList(lstCourse, sortSelected);
        }


        request.setAttribute("listCourseInTheYear", lstCourse);
        request.setAttribute("func", "listCourseInTheYear");
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

    public void getAmountStudent(List<Course> lstCourse, IEnrollmentDAO iEnrollmentDAO){
        System.out.println(lstCourse.size());
        if(lstCourse.size() > 0){
            for (Course course : lstCourse) {
                int amount = iEnrollmentDAO.countStudentOfCourse(course.getId());
                course.setAmountStudent(amount);
            }
        }

    }
}
