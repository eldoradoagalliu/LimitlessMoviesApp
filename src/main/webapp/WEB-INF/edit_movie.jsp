<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Movie</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/style2.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/description.css"/>
</head>

<body class="bg-image2">
<header class="pt-2 pb-1">
    <div class="d-flex align-items-center justify-content-between">
        <div>
            <div class="d-flex justify-content-start">
                <h1><a id="title-main" href="/">Limitless Movies</a></h1>
            </div>
            <p id="title-secondary">...watch from dusk till down...</p>
        </div>
        <div class="d-flex">
            <div class="ms-3 nav-item">
                <a href="/user/${currentUser.id}"><img id="title-img" src="${currentUser.photosImagePath}" alt="${currentUser.firstName}"></a>
                <a href="/user/${currentUser.id}" id="profile-style">${currentUser.firstName}'s Profile</a>
            </div>
            <div class="ms-2 nav-item" style="margin-top: 3px">
                <form action="/logout" method="post" id="logoutForm">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input class="logout" type="submit" value="Logout" style="font-size: 16px"/>
                </form>
            </div>
        </div>
    </div>
</header>

<div class="card movie" style="border-radius: 15px; background-color: rgba(238, 234, 234, 0.7)">
    <div class="card-body p-4">
        <div class="d-flex text-black">
            <div class="flex-grow-1 ms-3" style="font-weight: 600">
                <h2 class="mb-1">Edit Movie</h2>
                <%--@elvariable id="movie" type=""--%>
                <form:form action="/admin/movie/edit/${movie.id}" method="put" modelAttribute="movie">
                    <div class="h7 pt-1">
                        <form:label path="title">Movie Title: </form:label>
                        <form:input path="title" placeholder="Title" style="margin-left: 32px"/>
                        <div class="h7"><form:errors path="title" class="text-danger"></form:errors></div>
                    </div>

                    <div class="h7 pt-1">
                        <form:label path="genre">Movie Genre: </form:label>
                        <form:input path="genre" placeholder="Genre" style="margin-left: 20px"/>
                        <div class="h7"><form:errors path="genre" class="text-danger"></form:errors></div>
                    </div>

                    <div class="pt-2">
                        <a href="/admin" class="btn btn-outline-dark me-1" style="width: 110px">Cancel</a>
                        <button class="btn btn-dark" style="width: 210px">Edit Movie</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>