<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body class="admin-bg-image container" style="color: white">
<div class="d-flex align-content-center justify-content-between">
    <div style="margin-top: 10px">
        <h2>Welcome, ${currentUser.firstName} to the Admin Dashboard</h2>
    </div>
    <div style="margin-top: 20px" class="d-flex flex-row">
        <div>
            <a href="/admin/contact" class="btn btn-white" style="margin-right: 10px" title="Notify users for the upcoming movies">
                <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="white" class="bi bi-envelope" viewBox="0 0 16 16">
                <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
                </svg>
            </a>
        </div>
        <div>
            <a href="/admin/movie/new" class="btn btn-primary text-center">New Movie</a>
        </div>
        <div>
            <a href="/admin/cinema/new" class="btn btn-dark text-center" style="margin-left: 10px">New Cinema</a>
        </div>
        <div>
            <form id="logoutForm" method="POST" action="/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" value="Logout" class="btn btn-secondary" style="margin-left: 10px"/>
            </form>
        </div>
    </div>
</div>
<h2 style="border-bottom: 2px solid white">Add Movies for Cinemas</h2>
<table class="table black-color" style="margin-top: 10px; background-color:rgba(238, 234, 234, 0.7); font-weight: 600">
    <thead style="margin-left: 5px">
    <th>Cinema Name</th>
    <th>Movies Title</th>
    </thead>
    <c:forEach var="cinema" items="${cinemas}">
        <tbody>
            <tr>
                <td>
                    <div><a href="/cinema/${cinema.id}" class="black-color"><c:out value="${cinema.cinemaName}"/></a></div>
                </td>
                <td>
                    <ol class="black-color">
                        <c:forEach var="movie" items="${movies}">
                            <c:if test="${!cinema.getMovies().contains(movie)}">
                                <div class="d-flex align-content-center justify-content-between">
                                    <div>
                                        <li><a href="/movie/${movie.id}" class="black-color"><c:out value="${movie.title}"/></a></li>
                                    </div>

                                    <div class="d-flex" style="margin-bottom: 7px">
                                        <%--@elvariable id="ticket" type=""--%>
                                            <form:form action="/cinema/add/movie/${movie.id}" method="post" modelAttribute="ticket">
                                                <form:input path="price" type="number" min="0" placeholder="Ticket Price"/>
                                                <input type="time" placeholder="Time" name="time" required="required">
                                                <input type="date" placeholder="Date" name="date" required="required">
                                                <input name="cinemaId" value="${cinema.id}" type="hidden">
                                                <button class="btn btn-primary">Add Movie</button>
                                            </form:form>
                                            <div><form:errors path="cinemaDisplayTime" class="text-danger"></form:errors></div>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </ol>
                </td>
            </tr>
        </tbody>
    </c:forEach>
</table>

<h2 style="border-bottom: 2px solid white">Cinema Movies List</h2>
<table class="table black-color" style="margin-top: 10px; background-color:rgba(238, 234, 234, 0.7); font-weight: 600">
    <thead>
        <th>Movie Title</th>
    </thead>
    <c:forEach var="movie" items="${allMovies}">
    <tbody>
        <tr>
            <td>
                <div class="d-flex align-content-center justify-content-between">
                    <div>
                        <a href="/movie/${movie.id}" class="black-color"><c:out value="${movie.title}"/></a>
                    </div>

                    <div class="d-flex align-content-center">
                        <a href="/admin/movie/edit/${movie.id}" class="btn btn-success m-2">Edit Movie</a>
                        <form:form action="/admin/movie/delete/${movie.id}" method="post">
                            <input type="hidden" name="_method" value="delete">
                            <button class="btn btn-danger m-2" style="margin-left: 10px">Delete Movie</button>
                        </form:form>
                    </div>
                </div>
            </td>
        </tr>
    </tbody>
    </c:forEach>
</table>

<h2 style="border-bottom: 2px solid white">Cinemas</h2>
<table class="table black-color" style="margin-top: 10px; background-color:rgba(238, 234, 234, 0.7); font-weight: 600">
    <thead>
        <th>Cinema Name</th>
    </thead>
    <c:forEach var="cinema" items="${allCinemas}">
    <tbody>
        <tr>
            <td>
                <div class="d-flex align-content-center justify-content-between">
                    <div>
                        <a href="/cinema/${cinema.id}" class="black-color"><c:out value="${cinema.cinemaName}"/></a>
                    </div>
                    <div class="d-flex align-items-center">
                        <a href="/admin/cinema/edit/${cinema.id}" class="btn btn-success m-2">Edit Cinema</a>
                        <form:form action="/admin/cinema/delete/${cinema.id}" method="post">
                            <input type="hidden" name="_method" value="delete">
                            <button class="btn btn-danger m-2" style="margin-left: 10px">Delete Cinema</button>
                        </form:form>
                    </div>
                </div>
            </td>
        </tr>
    </tbody>
    </c:forEach>
</table>
</body>
</html>