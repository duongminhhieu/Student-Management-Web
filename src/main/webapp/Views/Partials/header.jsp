<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="alert alert-primary text-center" role="alert">
    <h4>Student Management Web - 20120473</h4>
</div>

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand mb-0 h1" href="/home">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/list-student">List Student</a>
                <a class="nav-link" href="/list-course">List Course</a>
            </div>
        </div>
    </div>
</nav>