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
    <link rel="stylesheet" href="/css/style.css"/>
    <link rel="stylesheet" href="/css/email.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body style="background-color: rgba(245,222,179,0.37)">
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
<div style="margin-top: 20%; text-align: center; color: #25d025">
    <h1>Emails sent successfully :)</h1>
</div>
</body>
</html>