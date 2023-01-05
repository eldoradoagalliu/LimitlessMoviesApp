<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Cinema</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/style3.css"/>
    <link rel="stylesheet" href="/css/description.css"/>
</head>

<body class="bg-image">
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

<div class="card cinema" style="border-radius: 15px; background-color: rgba(238, 234, 234, 0.7)">
    <div class="card-body p-4">
        <div class="d-flex text-black">
            <div class="flex-grow-1 ms-3" style="font-weight: 600">
                <h2 class="mb-1">Add a new Cinema</h2>
                <%--@elvariable id="cinema" type="java"--%>
                <form:form action="/admin/cinema/new" method="post" modelAttribute="cinema">
                    <div class="h7 pt-1">
                        <form:label path="cinemaName">Cinema Name: </form:label>
                        <form:input path="cinemaName" placeholder="Cinema" style="margin-left: 5px"/>
                        <div class="h7"><form:errors path="cinemaName" class="text-danger"></form:errors></div>
                    </div>

                    <div class="h7 pt-1">
                        <form:label path="latitude">Latitude: </form:label>
                        <form:input path="latitude" type="number" step="0.0000001" placeholder="Latitude" style="margin-left:47px"/>
                        <div class="h7"><form:errors path="latitude" class="text-danger"></form:errors></div>
                    </div>

                    <div class="h7 pt-1">
                        <form:label path="longitude">Longitude: </form:label>
                        <form:input path="longitude" type="number" step="0.0000001" placeholder="Longitude" style="margin-left: 33px"/>
                        <div class="h7"><form:errors path="longitude" class="text-danger"></form:errors></div>
                    </div>

                    <div class="pt-2">
                        <a href="/admin" class="btn btn-outline-dark me-1" style="width: 110px">Cancel</a>
                        <button class="btn btn-dark" style="width: 200px">Create</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>