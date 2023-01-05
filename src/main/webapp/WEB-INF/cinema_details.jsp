<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cinema Details</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/style4.css"/>
    <link rel="stylesheet" href="/css/description.css"/>
    <script type="module" src="cinema_details.jsp"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>

<body>
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
                <img  id="title-img" src="${currentUser.photosImagePath}" alt="${currentUser.firstName}">
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
<%--The function that renders the map from google api--%>
<div class="container">
    <div>
        <h1 style="color:#00bbff ; font-weight: 600; text-align: center"><c:out value="${cinema.cinemaName}"/>'s cinema</h1>
    </div>
    <div class="d-flex justify-content-between">
        <div class="comments" style="color: black; width: 150%; height: 400px; padding: 4%; margin-top: 3.5%">
            <div><h4>Movies available in <c:out value="${cinema.cinemaName}"/>'s cinema:</h4>
                <c:forEach var="movie" items="${movies}">
                    <div class="d-flex align-content-center justify-content-between" style="margin-top: 5%">
                        <div>
                            <a href="/movie/${movie.id}" style="text-decoration: none; color: black">
                                <h5><c:out value="${movie.title}"/></h5></a>
                        </div>

                        <c:if test="${!currentUser.getBookedMovies().contains(movie)}">
                        <div style="margin-bottom: 7px">
                            <form:form action="/cinema/book/${movie.id}" method="post">
                                <button class="btn btn-secondary">Book A Ticket!</button>
                            </form:form>
                        </div>
                        </c:if>

                        <c:if test="${currentUser.getBookedMovies().contains(movie)}">
                            <div style="font-size: 18px">You have already booked this movie!</div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div>
            <h2 style="text-align: center">Cinema location:</h2>
            <div id="map" style="margin-left: 40px; height: 400px; width: 500px; background: transparent; border: solid black"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function initMap() {
        const location = {lat: <c:out value="${cinema.latitude}"/>, lng: <c:out value="${cinema.longitude}"/>};

        const map = new google.maps.Map(document.getElementById("map"), {
            zoom: 16,
            center: location,
        });

        const marker = new google.maps.Marker({
            position: location,
            map: map,
        });
    }
    window.initMap = initMap;
</script>
<%--API Key - Insert your Google Maps API Key--%>
<script src="https://maps.googleapis.com/maps/api/js?key=your_key&callback=initMap&v=weekly"
        defer>
</script>
</body>
</html>