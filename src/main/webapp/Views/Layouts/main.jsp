<%@include file="../Partials/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta charset="ISO-8859-1">
    <title>Trang web môn học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>

<div class="container-fluid mt-2">
    <!-- header -->
    <%@ include file="../Partials/header.jsp" %>
</div>

<div class="app">

    <div class="container-fluid mt-2">
        <div class="card">
            <div class="card-header text-bg-primary">Main content</div>
            <div class="card-body overflow-auto">


                <c:choose>
                    <c:when test="${func == 'home'}">
                        <%@ include file="../HomePage.jsp" %>
                    </c:when>
                    <c:when test="${func == 'addStudent'}">
                        <%@ include file="../Student/addStudent.jsp" %>
                    </c:when>
                    <c:when test="${func == 'listStudent'}">
                        <%@ include file="../Student/listStudent.jsp" %>
                    </c:when>
                    <c:otherwise>
                        do this when nothing else is true
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <div class="container-fluid mt-2">
        <!-- footer -->
        <%@ include file="../Partials/footer.jsp" %>
    </div>


</body>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
        integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js"
        integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
</html>