<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body class="edit-profile container">
<div style="width: 600px; margin-left: 20%; margin-top: 8%">
    <div class="card" style="border-radius: 15px; background-color: rgba(238, 234, 234, 0.7)">
        <div class="card-body p-4">
            <h2 class="text-center">Edit Your Profile</h2>
            <div class="text-black d-flex align-content-center justify-content-between">
                <div style="margin-top: 8%; margin-left: 2%; width: 100%; height: 100%">
                    <img src="${user.photosImagePath}" alt="profile-image" class="edit-profile-photo"/>
                </div>
                <div class="mt-2" style="font-weight: 600; width: 440px">
                    <%--@elvariable id="user" type="java"--%>
                    <form:form action="/user/edit/${user.id}" method="post" modelAttribute="user">
                        <input type="hidden" name="_method" value="put">

                        <div class="p-1">
                            <form:label path="firstName" >First Name:</form:label>
                            <div><form:errors path="firstName" class="text-danger"/></div>
                            <form:input path="firstName" class="form-control" value="${user.firstName}"/>
                        </div>

                        <div class="p-1">
                            <form:label path="lastName">Last Name:</form:label>
                        <div><form:errors path="lastName" class="text-danger"/></div>
                        <form:input path="lastName" class="form-control" value="${user.lastName}"/>
                        </div>

                        <div class="p-1">
                            <form:label path="email">Email:</form:label>
                            <div><form:errors path="email" class="text-danger"/></div>
                            <form:input  class="form-control" path="email" value="${user.email}"/>
                        </div>

                        <div class="p-1">
                            <form:label path="birthdate">Birthdate:</form:label>
                            <div><form:errors path="birthdate" class="text-danger"/></div>
                            <form:input path="birthdate" class="form-control" type="date"/>
                        </div>

                        <div class="p-1">
                            <a href="/user/${user.id}" class="btn btn-outline-primary me-1" style="width: 100px">Cancel</a>
                            <button class="btn btn-primary" style="width: 100px">Confirm</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>