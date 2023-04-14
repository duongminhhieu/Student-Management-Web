<%@ page import="Dao.DAOFactory" %>
<%@ page import="Dao.ICourseDAO" %>
<%@ page import="Dao.IEnrollmentDAO" %>
<%@ page import="Dao.IStudentDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Dao.implementsDAO.CourseDAO" %>
<%@ page import="Models.CourseOfStudent" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../Partials/taglib.jsp" %>
<jsp:useBean id="scoreBoardInTheYear" scope="request" type="java.util.List"/>


<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/list-student">List Student</a></li>

        <li class="breadcrumb-item active" aria-current="page">Score board</li>
    </ol>
</nav>

<div>
    <%
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        // Obtain UserDAO.
        IStudentDAO iStudentDAO = javabase.getStudentDAO();

        Student student = iStudentDAO.find(request.getParameter("idStudent"));

        out.println("<div class=\"card mb-4\">\n" +
                "  <h5 class=\"card-header\">Score board Of Student</h5>\n" +
                "  <div class=\"card-body\">\n" +
                "    <h5 class=\"card-title\">" + student.getName() + "</h5>\n" +
                "    <p class=\"card-text\">ID: " + student.getId() + "</p>\n" +
                "    <p class=\"card-text\">Birthday: " + student.getBirthday() + "</p>\n" +
                "    <p class=\"card-text\">Grade: " + student.getGrade() + "</p>\n" +
                "    <p class=\"card-text\">Address: " + student.getAddress() + "</p>\n" +
                "    <p class=\"card-text\">Note: " + student.getNotes() + "</p>\n" +
                "  </div>\n" +
                "</div>");
    %>
</div>

<div class="d-flex justify-content-end me-4">

    <h5 class="me-3 mt-2">Grade: <span class="text-danger gradeText"><%
        float tb = 0;
        IEnrollmentDAO iEnrollmentDAO = javabase.getEnrollmentDAO();
        List<CourseOfStudent> ll = iEnrollmentDAO.lstCourseOfStudent(request.getParameter("idStudent"));
        for(CourseOfStudent c: ll){
            tb += c.getScore();
        }
        if(ll.size() == 0){
            out.println(0);
        }else
            out.println((float) tb/ll.size());
    %></span></h5>

<%--    <button class="btn btn-success mb-3 update-grade">Update Grade Database</button>--%>

    <%
        // lay list Year
        List<Integer> lstYear = new ArrayList<>();
        String idS = request.getParameter("idStudent");
        String yearCurrent = request.getParameter("year");
        List<Course> lstC = new ArrayList<Course>();
        ICourseDAO courseDAO1 = javabase.getCourseDAO();
        lstC = courseDAO1.listCourseOfStudent(idS);
        int sizeList = lstC.size();
        for (Course course1 : lstC) {
            if (!lstYear.contains(course1.getYear()))
                lstYear.add(course1.getYear());
        }

        out.println(" <select class=\"form-select mb-3 ms-2 select-box-year\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                "        <option selected >Year</option>\n");
        for (int y : lstYear) {
            if (yearCurrent != null && !yearCurrent.equals("all") && Integer.parseInt(yearCurrent) == y) {
                out.println("<option selected value=\"" + y + "\">" + y + "</option>\n");
            } else {
                out.println("<option value=\"" + y + "\">" + y + "</option>\n");
            }
        }
        out.println("    </select>");
    %>

</div>
<table class="table align-middle mb-0 bg-white">
    <thead class="bg-light">
    <tr>
        <th>#</th>
        <th>Course ID</th>
        <th>Name</th>
        <th>Score</th>
        <th>Year</th>
        <th>Enrollment Date</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${scoreBoardInTheYear}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td>
                    ${item.getId()}
            </td>
            <td>
                    ${item.getName()}
            </td>
            <td>
                    ${item.getScore()}
            </td>
            <td>
                ${item.getYear()}
            </td>
            <td>
                    ${item.getEnrollmentDate()}
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>

<script>

    function stringHandingYear(str, year) {
        var result;
        console.log(year)
        if (str.indexOf("?year") > -1) {
            result = str.split("?")[0];
            result = result + '?year=' + year;
        } else if (str.indexOf("?idStudent") > -1) {
            if (str.indexOf("&year") > -1) {
                result = str.split("&")[0];
                result = result + '&year=' + year;
            } else {
                result = str + '&year=' + year;
            }
        } else {
            result = str + '?year=' + year;
        }


        return result;
    }

    function getCourseByYear(year) {
        $.ajax({
            url: '/score-board-in-the-year',
            type: 'POST',
            contentType: 'application/json',
            data: stringHandingYear(window.location.href, year),
            success: function (result) {
                window.location.href = result
            },
            error: function (error) {

            }
        });
    }

    var selectBoxYear = document.querySelector('.select-box-year');
    selectBoxYear.addEventListener("change", function (evt) {
        console.log(selectBoxYear.value)
        if (selectBoxYear.value !== "Year")
            getCourseByYear(selectBoxYear.value);
        else {
            getCourseByYear('all');
        }
    });

    var updateGrade = document.querySelector('.update-grade');
    updateGrade.addEventListener('click' , function(evt) {

        var gradeT = document.querySelector('.gradeText');

        $.ajax({
            url: '/score-board-in-the-year',
            type: 'PUT',
            contentType: 'application/json',
            data: gradeT,
            success: function (result) {
                window.location.href
            },
            error: function (error) {

            }
        });
    })
</script>

