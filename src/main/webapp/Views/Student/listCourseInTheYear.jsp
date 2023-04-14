<%@ page import="Dao.DAOFactory" %>
<%@ page import="Dao.ICourseDAO" %>
<%@ page import="Dao.IEnrollmentDAO" %>
<%@ page import="Dao.IStudentDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Dao.implementsDAO.CourseDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../Partials/taglib.jsp" %>
<jsp:useBean id="listCourseInTheYear" scope="request" type="java.util.List"/>


<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/list-student">List Student</a></li>

        <li class="breadcrumb-item active" aria-current="page">Course List</li>
    </ol>
</nav>

<div>
    <%
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        // Obtain UserDAO.
        IStudentDAO iStudentDAO = javabase.getStudentDAO();

        Student student = iStudentDAO.find(request.getParameter("idStudent"));

        out.println("<div class=\"card mb-4\">\n" +
                "  <h5 class=\"card-header\">Course List Of Student</h5>\n" +
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
    <%
        String value = request.getParameter("selected");
        if (value != null) {

            if (value.equals("1")) {
                out.println(" <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                        "        <option >Sort</option>\n" +
                        "        <option selected value=\"1\">Sort name A - Z</option>\n" +
                        "        <option value=\"2\">Sort name Z - A</option>\n" +
                        "    </select>");
            } else if (value.equals("2")) {
                out.println(" <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                        "        <option >Sort</option>\n" +
                        "        <option value=\"1\">Sort name A - Z</option>\n" +
                        "        <option selected value=\"2\">Sort name Z - A</option>\n" +
                        "    </select>");
            }

        } else {
            out.println(" <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                    "        <option selected >Sort</option>\n" +
                    "        <option value=\"1\">Sort name A - Z</option>\n" +
                    "        <option value=\"2\">Sort name Z - A</option>\n" +
                    "    </select>");
        }

    %>

    <%
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
            if(yearCurrent != null && !yearCurrent.equals("all") && Integer.parseInt(yearCurrent) == y){
                out.println("<option selected value=\""+ y + "\">" + y + "</option>\n");
            } else {
                out.println("<option value=\""+ y + "\">" + y + "</option>\n");
            }
        }
        out.println("    </select>");
    %>

</div>
<div class="row lst-course">

    <c:forEach var="item" items="${listCourseInTheYear}" varStatus="loop">
        <div class="col-sm-4 mt-4">
            <div class="card">
                <div class="card-body bg-info p-2 bg-opacity-10">
                    <h4 class="card-title text-primary mt-3 fw-semibold">${item.getName()}</h4>
                    <h6 class="card-text mt-4">Course ID: <span class="text-warning">${item.getId()}</span></h6>
                    <h6 class="card-text mt-4">Lecture: <span class="text-warning">${item.getLecture()}</span></h6>
                    <h6 class="card-text mt-4">Year: <span class="text-warning">${item.getYear()}</span></h6>
                    <h6 class="card-text mt-4">Number Student: <span
                            class="text-warning">${item.getAmountStudent()}</span></h6>
                    <h6 class="card-text mt-4 mb-4">Note: <span class="text-warning">${item.getNotes()}</span></h6>

                </div>
            </div>
        </div>
    </c:forEach>
</div>

<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="deleteModalLabel">Delete Course</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Do you want to delete this course ?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger" id="btn-delete-user">Delete</button>
            </div>
        </div>
    </div>
</div>


<form name="delete-form" method="DELETE"></form>
<form name="sort-form" class="sort-form" method="get"></form>
<script>

    var userID;
    document.addEventListener("DOMContentLoaded", function () {
        // get ID from modal
        $('#deleteModal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget) // Button that triggered the modal
            userID = button.data('id')
        })
    });


    var deleteUser = document.getElementById('btn-delete-user');

    deleteUser.onclick = function (event) {
        //var formCourse = document.forms['delete-form'];
        //formCourse.action = `/list-student?idDelete=${userID}`;
        deleteCourse(userID);
        //formCourse.submit();
    }

    function deleteCourse(data) {
        $.ajax({
            url: '/list-course',
            type: 'DELETE',
            contentType: 'application/json',
            data: data,
            success: function (result) {
                window.location.href = "/list-course";
            },
            error: function (error) {
                window.location.href = "/list-course";
            }
        });
    }


    function stringHanding(str, selected) {
        var result;

        if (str.indexOf("?selected") > -1) {
            result = str.split("?")[0];
            result = result + '?selected=' + selected;
        } else if (str.indexOf("?idStudent") > -1) {
            if (str.indexOf("&selected") > -1) {
                result = str.split("&")[0];
                result = result + '&selected=' + selected;
            } else {
                result = str + '&selected=' + selected;
            }
        } else {
            result = str + '?selected=' + selected;
        }

        return result;
    }

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

    function sortCourse(selected) {
        $.ajax({
            url: '/list-course',
            type: 'POST',
            contentType: 'application/json',
            data: stringHanding(window.location.href, selected),
            success: function (result) {
                window.location.href = result
            },
            error: function (error) {

            }
        });
    }

    var selectBox = document.querySelector('.select-box');
    selectBox.addEventListener("change", function (evt) {
        if (selectBox.value !== "Sort")
            sortCourse(selectBox.value);
    });

    function getCourseByYear(year) {
        $.ajax({
            url: '/list-course-in-the-year',
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

</script>

