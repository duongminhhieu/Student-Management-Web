<%@ page import="java.util.Collections" %>
<%@ page import="Models.Student" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.text.Collator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="Models.Course" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="lstStudentOfCourse" scope="request" type="java.util.List"/>
<%@include file="../Partials/taglib.jsp" %>

<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/list-course">List Course</a></li>
        <li class="breadcrumb-item active" aria-current="page">View Student Of Course</li>
    </ol>
</nav>

<div>
    <%
        DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
        // Obtain UserDAO.
        ICourseDAO courseDAO = javabase.getCourseDAO();

        Course course = courseDAO.find(request.getParameter("idCourse"));

        out.println("<div class=\"card mb-4\">\n" +
                "  <h5 class=\"card-header\">Course Details</h5>\n" +
                "  <div class=\"card-body\">\n" +
                "    <h5 class=\"card-title\">" + course.getName() + "</h5>\n" +
                "    <p class=\"card-text\">Lecture: " + course.getLecture() + "</p>\n" +
                "    <p class=\"card-text\">Year: " + course.getYear() + "</p>\n" +
                "    <p class=\"card-text\">Note: " + course.getNotes() + "</p>\n" +
                "  </div>\n" +
                "</div>");
    %>
</div>

<div class="d-flex justify-content-end me-4">
    <a type="button" class="btn btn-success mb-3"
       href="${pageContext.request.contextPath}/add-student-of-course?idCourse=<%= request.getParameter("idCourse")%>">Add
        Student</a>

    <%
        String value = request.getParameter("selected");
        if (value != null) {

            if (value.equals("1")) {
                out.println("    <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                        "        <option>Sort</option>\n" +
                        "        <option selected value=\"1\">Sort name A - Z</option>\n" +
                        "        <option value=\"2\">Sort name Z - A</option>\n" +
                        "        <option value=\"3\">Sort score ASC</option>\n" +
                        "        <option value=\"4\">Sort score DESC</option>\n" +
                        "    </select>");
            } else if (value.equals("2")) {
                out.println("    <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                        "        <option>Sort</option>\n" +
                        "        <option value=\"1\">Sort name A - Z</option>\n" +
                        "        <option selected value=\"2\">Sort name Z - A</option>\n" +
                        "        <option value=\"3\">Sort score ASC</option>\n" +
                        "        <option value=\"4\">Sort score DESC</option>\n" +
                        "    </select>");
            } else if (value.equals("3")) {
                out.println("    <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                        "        <option>Sort</option>\n" +
                        "        <option value=\"1\">Sort name A - Z</option>\n" +
                        "        <option value=\"2\">Sort name Z - A</option>\n" +
                        "        <option selected value=\"3\">Sort score ASC</option>\n" +
                        "        <option value=\"4\">Sort score DESC</option>\n" +
                        "    </select>");
            } else if (value.equals("4")) {
                out.println("    <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                        "        <option>Sort</option>\n" +
                        "        <option value=\"1\">Sort name A - Z</option>\n" +
                        "        <option value=\"2\">Sort name Z - A</option>\n" +
                        "        <option value=\"3\">Sort score ASC</option>\n" +
                        "        <option selected value=\"4\">Sort score DESC</option>\n" +
                        "    </select>");
            }

        } else {
            out.println(" <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                    "        <option selected>Sort</option>\n" +
                    "        <option value=\"1\">Sort name A - Z</option>\n" +
                    "        <option value=\"2\">Sort name Z - A</option>\n" +
                    "        <option value=\"3\">Sort score ASC</option>\n" +
                    "        <option value=\"4\">Sort score DESC</option>\n" +
                    "    </select>");
        }

    %>


    <%--  <form class="d-flex mb-3 ms-3" role="search" action="${pageContext.request.contextPath}/list-student-of-course" method="get">--%>
    <%--    <input class="form-control me-2" type="search" name="search" placeholder="Search name ..." aria-label="Search"--%>
    <%--           value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">--%>
    <%--    <button class="btn btn-outline-success" type="submit">Search</button>--%>
    <%--  </form>--%>
</div>

<table class="table align-middle mb-0 bg-white">
    <thead class="bg-light">
    <tr>
        <th>#</th>
        <th>Student ID</th>
        <th>Name</th>
        <th>Score</th>
        <th>Birthday</th>
        <th>Address</th>
        <th>Note</th>
        <th>Enrollment Date</th>
        <th>Action</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${lstStudentOfCourse}" varStatus="loop">
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
                    ${item.getBirthday()}
            </td>
            <td>${item.getAddress()}</td>
            <td>
                    ${item.getNotes()}
            </td>
            <td>
                    ${item.getEnrollmentDate()}
            </td>
            <td>
                <div class="d-flex align-items-center">
                    <button type="button" class="ms-2 btn btn-danger btn-rounded" data-bs-toggle="modal"
                            data-id="${item.getId()}"
                            data-bs-target="#deleteModal">
                        Delete
                    </button>

                    <button type="button" class="btn btn-outline-success ms-4 dropdown-toggle"
                            data-bs-toggle="dropdown">
                        Enter Score
                    </button>

                    <form method="post" action="${pageContext.request.contextPath}/list-student-of-course?idStudent=${item.getId()}&idCourse=<%= request.getParameter("idCourse")%>" class="dropdown-menu p-4"
                          style="width: 200px;">
                        <div class="mb-3">
                            <label for="categoryName" class="form-label">Score</label>
                            <input type="text" class="form-control" id="categoryName"
                                   placeholder="Score" value="${item.getScore()}" name="score">
                        </div>
                        <button type="submit" class="btn btn-primary">OK</button>
                    </form>

                </div>


            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%--<ul class="pagination" id="pagination"></ul>--%>
<%--<input type="hidden" value="" id="page" name="page"/>--%>
<%--<input type="hidden" value="" id="maxPageItem" name="maxPageItem"/>--%>
<%--<input type="hidden" value="" id="sortName" name="sortName"/>--%>
<%--<input type="hidden" value="" id="sortBy" name="sortBy"/>--%>
<%--<input type="hidden" value="" id="type" name="type"/>--%>

<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="deleteModalLabel">Delete Student</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Do you want to delete this student ?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger" id="btn-delete-user">Delete</button>
            </div>
        </div>
    </div>
</div>


<form name="delete-form" method="DELETE"></form>

<script>
    <%--var totalPages = ${model.totalPage};--%>
    <%--var currentPage = ${model.page};--%>
    <%--var limit = 2;--%>
    <%--$(function () {--%>
    <%--    window.pagObj = $('#pagination').twbsPagination({--%>
    <%--        totalPages: totalPages,--%>
    <%--        visiblePages: 10,--%>
    <%--        startPage: currentPage,--%>
    <%--        onPageClick: function (event, page) {--%>
    <%--            if (currentPage != page) {--%>
    <%--                $('#maxPageItem').val(limit);--%>
    <%--                $('#page').val(page);--%>
    <%--                $('#sortName').val('title');--%>
    <%--                $('#sortBy').val('desc');--%>
    <%--                $('#type').val('list');--%>
    <%--                $('#formSubmit').submit();--%>
    <%--            }--%>
    <%--        }--%>
    <%--    });--%>
    <%--});--%>


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
        console.log(1)
        //var formCourse = document.forms['delete-form'];
        //formCourse.action = `/list-student?idDelete=${userID}`;
        deleteSt(userID, "${lstStudentOfCourse.size() > 0 ? lstStudentOfCourse.get(0).getIdCourse():0}");
        //formCourse.submit();
    }

    function deleteSt(idStudent, idCourse) {
        $.ajax({
            url: '/list-student-of-course',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify({idStudent: idStudent, idCourse: idCourse}),
            success: function (result) {
                window.location.href = "/list-student-of-course?idCourse=" + idCourse;
            },
            error: function (error) {
                //window.location.href = "/list-student";
            }
        });
    }


    function stringHanding(str, selected) {
        var result;

        if (str.indexOf("?selected") > -1) {
            result = str.split("?")[0];
            result = result + '?selected=' + selected;
        } else if (str.indexOf("?idCourse") > -1) {
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

    function sortStudent(selected) {
        $.ajax({
            url: '/list-student',
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
            sortStudent(selectBox.value);
    });

</script>

