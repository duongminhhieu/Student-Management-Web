<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 12/04/2023
  Time: 12:55 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>


<table class="table align-middle mb-0 bg-white">
    <thead class="bg-light">
    <tr>
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

    <c:forEach var="item" items="${lstSt}">

        <tr>
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
                    <button type="button" class="btn btn-primary btn-rounded">
                        Edit
                    </button>
                    <button type="button" class="ms-2 btn btn-danger btn-rounded">
                        Delete
                    </button>
                </div>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>