<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/04/2023
  Time: 7:53 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../Partials/taglib.jsp" %>
<jsp:useBean id="lstCourse" scope="request" type="java.util.List"/>




<div class="d-flex justify-content-end me-4">
    <a type="button" class="btn btn-success mb-3" href="${pageContext.request.contextPath}/add-course">Add Course</a>

    <select class="form-select mb-3 ms-2 select-box" style="width: auto" aria-label="Default select example">
        <option selected>Sort</option>
        <option value="1">Sort name A - Z</option>
        <option value="2">Sort name Z - A</option>
    </select>

    <form class="d-flex mb-3 ms-3" role="search" action="${pageContext.request.contextPath}/list-course" method="get">
        <input class="form-control me-2" type="search" name="search" placeholder="Search name ..." aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
    </form>
</div>
<div class="row">

    <c:forEach var="item" items="${lstCourse}" varStatus="loop">
        <div class="col-sm-4">
            <div class="card">
                <div class="card-body bg-info p-2 bg-opacity-10">
                    <h4 class="card-title text-primary mt-3 fw-semibold">${item.getName()}</h4>
                    <h6 class="card-text mt-4">Course ID: <span class="text-warning">${item.getId()}</span></h6>
                    <h6 class="card-text mt-4">Lecture: <span class="text-warning">${item.getLecture()}</span></h6>
                    <h6 class="card-text mt-4">Year: <span class="text-warning">${item.getYear()}</span></h6>
                    <h6 class="card-text mt-4">Number Student: <span class="text-warning">10</span></h6>
                    <h6 class="card-text mt-4 mb-4">Note: <span class="text-warning">${item.getNotes()}</span></h6>
                    <a href="#" class="btn btn-success">View List Student</a>
                    <a href="/edit-course" class="btn btn-primary">Edit Course</a>
                    <a href="#" class="btn btn-danger">Delete</a>

                </div>
            </div>
        </div>
    </c:forEach>
</div>
