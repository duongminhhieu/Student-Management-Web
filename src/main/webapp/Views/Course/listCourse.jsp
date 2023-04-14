<%@ page import="Dao.DAOFactory" %>
<%@ page import="Dao.ICourseDAO" %>
<%@ page import="Dao.IEnrollmentDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../Partials/taglib.jsp" %>
<jsp:useBean id="lstCourse" scope="request" type="java.util.List"/>
<jsp:useBean id="idCourse" class="java.lang.String" scope="page" />
<jsp:setProperty name="idCourse" property="*" />


<nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home">Home</a></li>
        <li class="breadcrumb-item active" aria-current="page">List Course</li>
    </ol>
</nav>

<div class="d-flex justify-content-end me-4">
    <a type="button" class="btn btn-success mb-3" href="${pageContext.request.contextPath}/add-course">Add Course</a>

    <%
        String value = request.getParameter("selected");
        if(value != null){

            if(value.equals("1")){
                out.println(" <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                        "        <option >Sort</option>\n" +
                        "        <option selected value=\"1\">Sort name A - Z</option>\n" +
                        "        <option value=\"2\">Sort name Z - A</option>\n" +
                        "    </select>");
            } else if(value.equals("2")){
                out.println(" <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                        "        <option >Sort</option>\n" +
                        "        <option value=\"1\">Sort name A - Z</option>\n" +
                        "        <option selected value=\"2\">Sort name Z - A</option>\n" +
                        "    </select>");
            }

        } else{
            out.println(" <select class=\"form-select mb-3 ms-2 select-box\" style=\"width: auto\" aria-label=\"Default select example\">\n" +
                    "        <option selected >Sort</option>\n" +
                    "        <option value=\"1\">Sort name A - Z</option>\n" +
                    "        <option value=\"2\">Sort name Z - A</option>\n" +
                    "    </select>");
        }

    %>

    <form class="d-flex mb-3 ms-3" role="search" action="${pageContext.request.contextPath}/list-course" method="get">
        <input class="form-control me-2" type="search" name="search" placeholder="Search name ..." aria-label="Search" value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
        <button class="btn btn-outline-success" type="submit">Search</button>
    </form>
</div>
<div class="row">

    <c:forEach var="item" items="${lstCourse}" varStatus="loop">
        <div class="col-sm-4 mt-4">
            <div class="card">
                <div class="card-body bg-info p-2 bg-opacity-10">
                    <h4 class="card-title text-primary mt-3 fw-semibold">${item.getName()}</h4>
                    <h6 class="card-text mt-4">Course ID: <span class="text-warning">${item.getId()}</span></h6>
                    <h6 class="card-text mt-4">Lecture: <span class="text-warning">${item.getLecture()}</span></h6>
                    <h6 class="card-text mt-4">Year: <span class="text-warning">${item.getYear()}</span></h6>
                    <h6 class="card-text mt-4">Number Student: <span class="text-warning">${item.getAmountStudent()}</span></h6>
                    <h6 class="card-text mt-4 mb-4">Note: <span class="text-warning">${item.getNotes()}</span></h6>
                    <a href="${pageContext.request.contextPath}/list-student-of-course?idCourse=${item.getId()}" class="btn btn-success">View List Student</a>
                    <a href="${pageContext.request.contextPath}/edit-course?idCourse=${item.getId()}"
                       class="btn btn-primary">Edit Course</a>
                    <button class="btn btn-danger" data-bs-toggle="modal"
                            data-id="${item.getId()}"
                            data-bs-target="#deleteModal">Delete</button>

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


    function stringHanding(str, selected){
        var result;

        if(str.indexOf("?selected") > -1){
            result = str.split("?")[0];
            result = result + '?selected=' + selected;
        } else if ( str.indexOf("?search") > -1) {
            if(str.indexOf("&selected") > -1){
                result = str.split("&")[0];
                result = result + '&selected=' + selected;
            }else {
                result = str + '&selected=' + selected;
            }
        } else {
            result = str + '?selected=' + selected;
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
        sortCourse(selectBox.value);
    });

</script>

