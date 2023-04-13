<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 05/04/2023
  Time: 3:39 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form class="row g-3" method="post" action="${pageContext.request.contextPath}/add-student">
    <div class="col-md-6">
        <label for="inputEmail4" class="form-label">Student ID</label>
        <input type="text" class="form-control" id="inputEmail4" name="id" required>
    </div>
    <div class="col-md-6">
        <label for="name" class="form-label">Name</label>
        <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="col-md-6">
        <label for="inputAddress" class="form-label">Grade</label>
        <input type="number" step="0.01" class="form-control" id="inputAddress" name="grade" placeholder="Grade">
    </div>
    <div class="col-md-6">
        <label for="birthday" class="form-label">Birthday</label>
        <input type="date" class="form-control" id="birthday" name="birthday" required>
    </div>
    <div class="col-md-6">
        <label for="inputCity" class="form-label">Address</label>
        <input type="text" class="form-control" id="inputCity" name="address" required>
    </div>
<%--    <div class="col-md-4">--%>
<%--        <label for="inputState" class="form-label">State</label>--%>
<%--        <select id="inputState" class="form-select">--%>
<%--            <option selected>Choose...</option>--%>
<%--            <option>...</option>--%>
<%--        </select>--%>
<%--    </div>--%>
    <div class="col-md-6">
        <label for="inputZip" class="form-label">Notes</label>
        <input type="text" class="form-control" id="inputZip" name="note" required>
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
        <button type="submit" class="btn btn-primary">Add Student</button>
    </div>
</form>