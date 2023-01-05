<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Movie Details</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/description.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>

<body class="background">
<header class="pt-2 pb-1">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <div class="d-flex justify-content-start">
                <h1><a id="title-main" href="/">Limitless Movies</a></h1>
            </div>
            <p id="title-secondary">...watch from dusk till down...</p>
        </div>
        <div class="d-flex">
            <div class="ms-3 nav-item">
                <a href="/user/${currentUser.id}"><img  id="title-img" src="${currentUser.photosImagePath}" alt="${currentUser.firstName}"></a>
                <a href="/user/${currentUser.id}" id="profile-style">${currentUser.firstName}'s Profile</a>
            </div>
            <div class="ms-3 nav-item" style="margin-top: 2px">
                <form id="logoutForm" method="POST" action="/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input class="logout" class="ms-3 nav-item" type="submit" value="Logout" />
                </form>
            </div>
        </div>
    </div>
</header>

<div class="content">
    <div>
        <h1 style="text-align: center">Details of "<c:out value="${movie.title}"/>"</h1>
        <div class="d-flex align-content-center justify-content-between">
            <h3>Movie Genre: <c:out value="${movie.genre}"/></h3>
            <div>
                <h3 style="margin-right: 10px">Watch Youtube Trailer:</h3>
                <a href="https://www.youtube.com/results?search_query=${movie.title}+trailer" target="_blank" class="btn btn-outline-danger" style="margin-left: 180px">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-youtube" viewBox="0 0 16 16">
                        <path d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.007 2.007 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.007 2.007 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31.4 31.4 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.007 2.007 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A99.788 99.788 0 0 1 7.858 2h.193zM6.4 5.209v4.818l4.157-2.408L6.4 5.209z"/>
                    </svg>
                    YouTube
                </a>
            </div>
        </div>

    </div>

    <div>
        <div>
            <h4 style="color: blue">Cinemas where this movie will be shown:</h4>
            <c:forEach var="cinema" items="${cinemas}">
                <c:forEach var="ticket" items="${cinema.getTickets()}">
                <div class="d-flex align-content-center justify-content-between" style="margin-top: 5px">
                    <div><h4 style="margin-top: 5px"><a style="text-decoration: none; color: black" href="/cinema/${cinema.id}"><c:out value="${cinema.cinemaName}"/></a></h4> </div>
                    <c:if test="${!currentUser.getBookedMovies().contains(movie)}">
                    <div>
                        <c:if test="${ticket.getCinema() == cinema}">
                        <div class="d-flex">
                            <div style="margin-bottom: 10px; margin-right: 10px; border: 2px solid black; padding: 10px; font-size: 18px">
                                <div>Price: <c:out value="${ticket.price}"/> Lekë</div>
                                Display Date: <fmt:formatDate pattern="HH:mm, d MMMM yyyy" value="${ticket.cinemaDisplayTime}"/>
                            </div>

                            <form:form action="/cinema/book/${movie.id}">
                                <button class="btn btn-secondary" style="margin-top: 15px">Book A Ticket!</button>
                            </form:form></div>
                        </c:if>
                    </div>
                    </c:if>

                    <c:if test="${currentUser.getBookedMovies().contains(movie)}">
                        <div style="font-size: 18px">You have already booked this movie!</div>
                    </c:if>
                </div>
                </c:forEach>
            </c:forEach>
        </div>
    </div>

    <div style="margin-top: 5%">
        <div class="comments">
            <h4>Leave your comments here :)</h4>
            <c:forEach var="comment" items="${comments}">
                <div><span><c:out value="${comment.user.getFullName()}"/>: </span><c:out value="${comment.content}"/></div>
            </c:forEach>
        </div>
        <div class="send-comments">
            <%--@elvariable id="comment" type="java"--%>
            <form:form action="/movie/comment/${movie.id}" method="post" modelAttribute="comment">
                <form:input path="content" placeholder="Write a comment" required="required"/>
                <button class="style-button" style="border-radius: 5px; border: none ;background-color: rgba(69,128,159,0.66)">Send Comment</button>
                <div><form:errors path="content" class="text-danger"/></div>
            </form:form>
        </div>
    </div>
</div>

<script src="https://apis.google.com/js/platform.js"></script>
</body>
</html>