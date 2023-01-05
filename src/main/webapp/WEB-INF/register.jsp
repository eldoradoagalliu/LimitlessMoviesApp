<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/image.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>
<body class="bg-image2">
<div class="container">
    <div class="row d-flex justify-content-center" style="width: 1000px; height: 1100px">
        <div class="col-lg-8 col-xl-6 d-flex align-items-center">
            <div class="card-body p-4" style="border-radius: 1rem; background-color:rgba(238, 234, 234, 0.31); font-weight: 600; color: white">
                <div class="d-flex align-items-center text-center">
                    <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219"></i>
                        <h3 class="h2 mb-0">&#127916; Let's watch a movie </h3>
                </div>
                <h3 class="mb-4 pb-2 pb-md-0 text-center" style="margin-top: 10px">Register here!</h3>

                <div style="font-size: 16px">
                    <form:errors path="user.*"/>
                </div>
                <%--@elvariable id="user" type="java"--%>
                <form:form action="/register" method="post" modelAttribute="user" enctype="multipart/form-data">
                    <div class="form-outline mb-4">
                        <form:label path="firstName" class="form-label">First Name: </form:label>
                        <form:input path="firstName" class="form-control"/>
                    </div>

                    <div class="form-outline mb-4">
                        <form:label path="lastName" class="form-label">Last Name: </form:label>
                        <form:input path="lastName" class="form-control" />
                    </div>

                    <div class="form-outline mb-4">
                        <form:label path="email" class="form-label">Email: </form:label>
                        <form:input path="email" class="form-control"/>
                    </div>

                    <div class="form-outline mb-4">
                        <form:label path="password" class="form-label">Password: </form:label>
                        <form:password path="password" class="form-control"/>
                    </div>

                    <div class="form-outline mb-4">
                        <form:label class="form-label" path="confirm">Password Confirmation: </form:label>
                        <form:password class="form-control" path="confirm"/>
                    </div>

                    <div class="form-outline mb-4">
                        <form:label path="birthdate" class="form-label">Birthdate: </form:label>
                        <form:input path="birthdate" type="date" class="form-control"/>
                    </div>

                    <div class="d-flex"><input type="submit" class=" btn btn-dark flex-grow-1 " value="Sign Up"/></div>
                </form:form>
                <hr>
                <div class="pb-lg-2 text-center" style="color: white; font-size: 18px">Already have an account? <a href="/login" style="color: white">Sing in</a></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>