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
    <link rel="stylesheet" href="/css/description.css"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body class="page-body" style="background-color: rgba(40,52,52,255)">

<header class="pt-2 pb-1">
    <div class="d-flex justify-content-between align-items-center">
        <div>
            <div class="d-flex justify-content-start">
                <h1><a id="title-main" href="/">Limitless Movies</a></h1>
            </div>
            <p id="title-secondary">...watch from dusk till down...</p>
        </div>
        <div class="d-flex">
            <c:choose>
            <c:when test="${empty sessionScope.user}">
            <form th:action="@{/}">
                &nbsp;
                <input placeholder="Search" type="text" name="keyword" id="keyword" size="50" th:th:value="${keyword}" style="width: 200px; border-radius: 5px; padding: 5px" required="required"/>
                &nbsp;
               <button style="background-color: transparent">
                   <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-search" viewBox="0 0 16 16">
                       <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                   </svg>
               </button>
                &nbsp;
                <input type="button" value="Clear" id="btnClear" onclick="clearSearch()" class="btn btn-success" style="margin-right: 10px; padding: 3px 8px"/>
            </form>
            <div class="ms-3 nav-item">
                <a href="/user/${currentUser.id}"><img id="title-img" src="${currentUser.photosImagePath}" alt="profile-photo"></a>
                <a href="/user/${currentUser.id}" class="style-a" id="profile-style">${currentUser.firstName}'s Profile</a>
            </div>
            <div class="ms-3 nav-item" style="margin-top: 2px">
                <form id="logoutForm" method="post" action="/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input class="logout" class="ms-3 nav-item" type="submit" value="Logout"/>
                </form>
            </div>
            </c:when>
            </c:choose>
        </div>
    </div>
</header>

<div class="container main-content" style="margin-top: 10%; margin-left: 5%">
    <h1 class="genre">Cinema movies</h1>
    <table class="table table-hover table-borderless table-striped table-dark">
        <thead style="border-bottom: 2px solid white">
        <tr class="style-table">
            <th scope="col" style="border-right: 2px solid white">Movie Title</th>
            <th scope="col">Movie Genre</th>
        </tr>
        </thead>
        <c:forEach var="movie" items="${movies}">
                <tbody class="style-table table-dark">
                <tr>
                    <td style="border-right: 2px solid white"><a href="/movie/${movie.id}" class="style-a">${movie.title}</a></td>
                    <td> ${movie.genre}</td>
                </tr>
                </tbody>
        </c:forEach>
    </table>

    <div>
        <h2 class="genre">Thriller Movies</h2>
        <div class="d-flex flex-row">
            <c:forEach var="thriller" items="${resultsThriller}">
                <div class="p-2">
                    <div class="card-img">
                        <img src="${thriller.getString("Poster")}" class="image-card">
                    </div>

                    <div class="description my-2 movie-card">
                        <p class="style-p" >Watch on Netflix: <a class="style-a" href="https://www.netflix.com/search?q=${thriller.getString("Title")}" target="_blank">${thriller.getString("Title")}</a></p>
                        <p class="style-p">Watch on Filma24: <a class="style-a" href="https://www.filma24.ch/search/${thriller.getString("Title")}" target="_blank">${thriller.getString("Title")}</a></p>
                        <p style="color:white">Release Year: ${thriller.getString("Year")}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <h2 class="genre">Comedy Movies</h2>
    <div class="d-flex flex-row">
        <c:forEach var="comedy" items="${resultsComedy}">
            <div class="p-2 .bg-transparent">
                <div class="card-img">
                    <img src="${comedy.getString("Poster")}" class="image-card">
                </div>

                <div class="description my-2 movie-card">
                    <p class="style-p" >Watch on Netflix: <a class="style-a" href="https://www.netflix.com/search?q=${comedy.getString("Title")}" target="_blank">${comedy.getString("Title")}</a></p>
                    <p class="style-p" >Watch on Filma24: <a class="style-a" href="https://www.filma24.ch/search/${comedy.getString("Title")}" target="_blank">${comedy.getString("Title")}</a></p>
                    <p style="color: white">Release Year: ${comedy.getString("Year")}</p>
                </div>
            </div>
        </c:forEach>
    </div>

    <div>
        <h2 class="genre">Action Movies</h2>
        <div class="d-flex flex-row">
            <c:forEach var="action" items="${resultsAction}">
                <div class="p-2">
                    <div class="card-img">
                        <img src="${action.getString("Poster")}" class="image-card">
                    </div>

                    <div class="description my-2 movie-card">
                        <p class="style-p" >Watch on Netflix: <a class="style-a" href="https://www.netflix.com/search?q=${action.getString("Title")}" target="_blank">${action.getString("Title")}</a></p>
                        <p class="style-p">Watch on Filma24: <a class="style-a" href="https://www.filma24.ch/search/${action.getString("Title")}" target="_blank">${action.getString("Title")}</a></p>
                        <p style="color:white">Release Year: ${action.getString("Year")}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div>
        <h2 class="genre">Anime Movies</h2>
        <div class="d-flex flex-row">
            <c:forEach var="anime" items="${resultsAnime}">
                <div class="p-2">
                    <div class="card-img">
                        <img src="${anime.getString("Poster")}" class="image-card">
                    </div>

                    <div class="description my-2 movie-card">
                        <p class="style-p" >Watch on Netflix: <a class="style-a" href="https://www.netflix.com/search?q=${anime.getString("Title")}" target="_blank">${anime.getString("Title")}</a></p>
                        <p class="style-p">Watch on Filma24: <a class="style-a" href="https://www.filma24.ch/search/${anime.getString("Title")}" target="_blank">${anime.getString("Title")}</a></p>
                        <p style="color:white">Release Year: ${anime.getString("Year")}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div>
        <h2 class="genre">Drama Movies</h2>
        <div class="d-flex flex-row">
            <c:forEach var="drama" items="${resultsDrama}">
                <div class="p-2">
                    <div class="card-img">
                        <img src="${drama.getString("Poster")}" class="image-card">
                    </div>

                    <div class="description my-2 movie-card">
                        <p class="style-p" >Watch on Netflix: <a class="style-a" href="https://www.netflix.com/search?q=${drama.getString("Title")}" target="_blank">${drama.getString("Title")}</a></p>
                        <p class="style-p">Watch on Filma24: <a class="style-a" href="https://www.filma24.ch/search/${drama.getString("Title")}" target="_blank">${drama.getString("Title")}</a></p>
                        <p style="color:white">Release Year: ${drama.getString("Year")}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <h2 class="genre">Horror Movies</h2>
    <div class="d-flex flex-row">
        <c:forEach var="horror" items="${resultsHorror}">
            <div class="p-2 .bg-transparent ">
                <div class="card-img">
                    <img src="${horror.getString("Poster")}" class="image-card">
                </div>

                <div class="description my-2 movie-card">
                    <p class="style-p">Watch on Netflix: <a class="style-a" href="https://www.netflix.com/search?q=${horror.getString("Title")}" target="_blank">${horror.getString("Title")}</a></p>
                    <p class="style-p">Watch on Filma24: <a class="style-a" href="https://www.filma24.ch/search/${horror.getString("Title")}" target="_blank">${horror.getString("Title")}</a></p>
                    <p style="color: white">Release Year: ${horror.getString("Year")}</p>
                </div>
            </div>
        </c:forEach>
    </div>

</div>
    <%--//script for clear button--%>
    <script type="text/javascript">
        function clearSearch() {
            window.location = "/", true;
        }
    </script>
</body>
</html>