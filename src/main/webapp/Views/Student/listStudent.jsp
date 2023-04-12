<%@ page import="java.util.Collections" %>
<%@ page import="Models.Student" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.text.Collator" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="lstSt" scope="request" type="java.util.List"/>
<%@include file="../Partials/taglib.jsp" %>


<div class="d-flex justify-content-end me-4">
    <a type="button" class="btn btn-success mb-3" href="${pageContext.request.contextPath}/add-student">Add Student</a>

    <select class="form-select mb-3 ms-2 select-box" style="width: auto" aria-label="Default select example">
        <option selected>Sort</option>
        <option value="1">Sort name A - Z</option>
        <option value="2">Sort name Z - A</option>
        <option value="3">Sort grade ASC</option>
        <option value="4">Sort grade DESC</option>
    </select>

    <form class="d-flex mb-3 ms-3" role="search" action="${pageContext.request.contextPath}/list-student" method="get">
        <input class="form-control me-2" type="search" name="search" placeholder="Search name ..." aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
    </form>
</div>

<table class="table align-middle mb-0 bg-white">
    <thead class="bg-light">
    <tr>
        <th>#</th>
        <th>Student ID</th>
        <th>Name</th>
        <th>Grade</th>
        <th>Birthday</th>
        <th>Address</th>
        <th>Note</th>
        <th>Action</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${lstSt}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td>
                    ${item.getId()}
            </td>
            <td>
                    ${item.getName()}
            </td>
            <td>
                    ${item.getGrade()}
            </td>
            <td>
                    ${item.getBirthday()}
            </td>
            <td>${item.getAddress()}</td>
            <td>
                    ${item.getNotes()}
            </td>
            <td>
                <div class="d-flex align-items-center">
                    <a href="${pageContext.request.contextPath}/edit-student?idStudent=${item.getId()}" type="button"
                       class="btn btn-primary btn-rounded">
                        Edit
                    </a>
                    <button type="button" class="ms-2 btn btn-danger btn-rounded" data-bs-toggle="modal"
                            data-id="${item.getId()}"
                            data-bs-target="#deleteModal">
                        Delete
                    </button>
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
        //var formCourse = document.forms['delete-form'];
        //formCourse.action = `/list-student?idDelete=${userID}`;
        deleteSt(userID);
        //formCourse.submit();
    }

    function deleteSt(data) {
        $.ajax({
            url: '/list-student',
            type: 'DELETE',
            contentType: 'application/json',
            data: data,
            success: function (result) {
                window.location.href = "/list-student";
            },
            error: function (error) {
                window.location.href = "/list-student";
            }
        });
    }

    <%

    Collator collator = Collator.getInstance(new Locale("vi", "VN"));
  %>


    var selectBox = document.querySelector('.select-box');
    selectBox.addEventListener("change", function (evt) {
        var table = document.querySelector(".table");
        var selectedValue = evt.target.value;
        window.location.href = "list-student?selectedValue=" + selectedValue;

        <%
          String selectedValue = request.getParameter("selectedValue");
            if (selectedValue != null && selectedValue.equals("1")) {
                    collator.setStrength(Collator.SECONDARY); // Không phân biệt chữ hoa, chữ thường,
                // Sắp xếp List theo thứ tự từ A-Z của key
                   Collections.sort(lstSt, new Comparator<Student>() {
                       public int compare(Student o1, Student o2) {
                           return collator.compare(o1.getName(), o2.getName());
                       }
                   });
              } else if (selectedValue != null && selectedValue.equals("2")) {
                  collator.setStrength(Collator.SECONDARY); // Không phân biệt chữ hoa, chữ thường,
             // Sắp xếp List theo thứ tự từ Z- A của key
                Collections.sort(lstSt, new Comparator<Student>() {
                    public int compare(Student o1, Student o2) {
                        return collator.compare(o2.getName(), o1.getName());
                    }
                });
              } else if (selectedValue != null && selectedValue.equals("3"))  {
                   Collections.sort(lstSt, new Comparator<Student>() {
                    public int compare(Student o1, Student o2) {
                        if (o1.getGrade() > o2.getGrade()) {
                            return 1;
                        } else if (o1.getGrade() < o2.getGrade()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                    });
              }
        %>


        table.innerHTML = `<thead class="bg-light">
                    <tr>
                        <th>#</th>
                        <th>Student ID</th>
                        <th>Name</th>
                        <th>Grade</th>
                        <th>Birthday</th>
                        <th>Address</th>
                        <th>Note</th>
                        <th>Action</th>

                    </tr>
                    </thead>
                    <tbody>` + `<c:forEach var="item" items="${lstSt}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>
                                    ${item.getId()}
                            </td>
                            <td>
                                    ${item.getName()}
                            </td>
                            <td>
                                    ${item.getGrade()}
                            </td>
                            <td>
                                    ${item.getBirthday()}
                            </td>
                            <td>${item.getAddress()}</td>
                            <td>
                                    ${item.getNotes()}
                            </td>
                            <td>
                                <div class="d-flex align-items-center">
                                    <a href="${pageContext.request.contextPath}/edit-student?idStudent=${item.getId()}" type="button"
                                       class="btn btn-primary btn-rounded">
                                        Edit
                                    </a>
                                    <button type="button" class="ms-2 btn btn-danger btn-rounded" data-bs-toggle="modal"
                                            data-id="${item.getId()}"
                                            data-bs-target="#deleteModal">
                                        Delete
                                    </button>
                                </div>

                            </td>
                        </tr>
                    </c:forEach>` + `</tbody>`
    });


</script>

