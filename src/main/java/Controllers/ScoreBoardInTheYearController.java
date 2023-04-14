package Controllers;

import Dao.DAOFactory;
import Dao.ICourseDAO;
import Dao.IEnrollmentDAO;
import Models.Course;
import Models.CourseOfStudent;
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

@WebServlet("/score-board-in-the-year")
public class ScoreBoardInTheYearController extends HttpServlet {
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


        List<CourseOfStudent> lstCourse = new ArrayList<CourseOfStudent>();
        if(yearCurrent != null && !yearCurrent.equals("all")){
            for(CourseOfStudent c : iEnrollmentDAO.lstCourseOfStudent(idStudent)){
                if(c.getYear() == Integer.parseInt(yearCurrent)){
                    lstCourse.add(c);
                }
            }
        } else {
            lstCourse = iEnrollmentDAO.lstCourseOfStudent(idStudent);
        }

        // laasy so luong hs cua khoa hoc
        getAmountStudent(lstCourse, iEnrollmentDAO);
        request.setAttribute("title", "Score Board");

        request.setAttribute("scoreBoardInTheYear", lstCourse);
        request.setAttribute("func", "scoreBoardInTheYear");
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

    public void getAmountStudent(List<CourseOfStudent> lstCourse, IEnrollmentDAO iEnrollmentDAO){
        System.out.println(lstCourse.size());
        if(lstCourse.size() > 0){
            for (Course course : lstCourse) {
                int amount = iEnrollmentDAO.countStudentOfCourse(course.getId());
                course.setAmountStudent(amount);
            }
        }

    }
}
