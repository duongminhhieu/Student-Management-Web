<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/04/2023
  Time: 9:12 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form class="row g-3" method="post" action="${pageContext.request .contextPath}/edit-course">
  <div class="col-md-6">
    <label for="inputEmail4" class="form-label">Course ID</label>
    <input type="text" class="form-control" id="inputEmail4" name="id" placeholder="Enter Course ID ..." value="${course.getId()}" readonly>
  </div>
  <div class="col-md-6">
    <label for="name" class="form-label">Name</label>
    <input type="text" class="form-control" id="name" name="name" placeholder="Enter name ..." value="${course.getName()}" required>
  </div>
  <div class="col-md-6">
    <label for="inputAddress" class="form-label">Lecture</label>
    <input type="text"  class="form-control" id="inputAddress" name="lecture" placeholder="Enter Lecture ..." value="${course.getLecture()}" required>
  </div>
  <div class="col-md-6">
    <label for="birthday" class="form-label">Year</label>
    <input type="number" class="form-control" id="birthday" name="year" value="${course.getYear()}" required>
  </div>
  <div class="col-md-6">
    <label for="inputZip" class="form-label">Notes</label>
    <input type="text" class="form-control" id="inputZip" name="note" value="${course.getNotes()}" required>
  </div>
  <%--    <div class="col-12">--%>
  <%--        <div class="form-check">--%>
  <%--            <input class="form-check-input" type="checkbox" id="gridCheck">--%>
  <%--            <label class="form-check-label" for="gridCheck">--%>
  <%--                Check me out--%>
  <%--            </label>--%>
  <%--        </div>--%>
  <%--    </div>--%>
  <div class="col-12">
    <button type="submit" class="btn btn-primary">Update Course</button>
  </div>
</form>