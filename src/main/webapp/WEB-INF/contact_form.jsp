<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/email.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body class="background">
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
            <div class="ms-2 nav-item" style="margin-top: 2px">
                <form action="/logout" method="post" id="logoutForm">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input class="logout" type="submit" value="Logout" style="font-size: 16px"/>
                </form>
            </div>
        </div>
    </div>
</header>

<div class="container" style="margin:10% 25%">
    <div class="content" style="align-items: center">
        <form:form action="/admin/contact" method="post">
            <div>
                <label>Subject</label>
                <input name="subject" placeholder="Email Subject" required="required"/>
            </div>
            <div>
                <textarea placeholder="Email Content" rows="10" style="width: 100%; margin-top: 3%; padding: 10px" name="content"></textarea>
            </div>
            <div style="margin-left: 410px">
                <button class="btn btn-light" style="background-color: rgba(239,112,112,0.37)"><a style="text-decoration: none; color: black; font-weight: 600" href="/">Cancel</a></button>
                <button class="btn btn-light" style="color: black;background-color: rgba(38,117,38,0.44); font-weight: 600">Send E-mail</button>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>