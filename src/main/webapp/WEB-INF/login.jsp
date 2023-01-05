<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/image.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>
<body class="bg-image" style="font-size: small; font-weight: 600">
<div class="container py-5" style="width: 800px">
    <div class="row d-flex justify-content-center">
        <div class="col-md-6 col-lg-7 d-flex align-items-center">
            <div class="card-body p-4 text-black" style="border-radius: 1rem; background-color:rgba(238, 234, 234, 0.31)">
                <c:if test="${logoutMessage != null}">
                    <div class="text-success text-center" style="font-size: 18px"><c:out value="${logoutMessage}"></c:out></div>
                </c:if>
                <c:if test="${errorMessage != null}">
                    <div class="text-danger text-center" style="font-size: 18px"><c:out value="${errorMessage}"></c:out></div>
                </c:if>
                <div class="d-flex align-items-center mb-3 pb-1">
                    <i class="fas fa-cubes fa-2x me-3" style="color: #ff6219"></i>
                    <span class="h3 mb-0">&#127916; Do you want to watch a movie together?</span>
                </div>
                <h4 class="mb-3 pb-3 text-center" style="letter-spacing: 1px">Sign in to your account!</h4>

                <form action="/login" method="post">
                    <div class="form-outline mb-4">
                        <label class="form-label">Email:</label>
                        <input type="text" id="email" class="form-control form-control-lg" name="email"/>
                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label">Password:</label>
                        <input type="password" id="password" class="form-control form-control-lg" name="password"/>
                    </div>

                    <div class="pt-1 mb-4 d-flex">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" class="btn btn-dark btn-block flex-grow-1" value="Sign In"/>
                    </div>
                </form>
                <hr>
                <p class="mb-5 pb-lg-2 text-center" style="color: black; font-size: 18px">Don't have an account?
                    <a href="/register" style="color: black">Register here</a>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>