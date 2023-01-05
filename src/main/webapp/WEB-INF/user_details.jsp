<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile Details</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body class="profile container" style="font-size: 18px">
<section class="vh-100">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col col-md-9 col-lg-7 col-xl-5">
                <div class="card" style="border-radius: 15px;background-color: rgba(238, 234, 234, 0.7)">
                    <div class="card-body p-4">
                        <img src="${user.photosImagePath}"
                             alt="${user.getFullName()} image" class="profile-photo" style="border-radius: 20px; margin-left: 35%"/>
                        <div class="d-flex text-black">
                            <div class="flex-grow-1 ms-3 text-center" style="font-weight: 600">
                                <%--@elvariable id="user" type=""--%>
                                <form:form class="p-3" action="/user/${user.id}" method="post" modelAttribute="user" enctype="multipart/form-data">

                                    <h4 class="mb-2">Details of <c:out value="${user.getFullName()}"/></h4>
                                    <div>Email: <c:out value="${user.email}"/></div>
                                    <div>
                                        Birthdate: <fmt:formatDate pattern="d MMMM yyyy" value="${user.birthdate}"/>
                                    </div>
                                    <div>
                                        Last Login: <fmt:formatDate pattern="HH:mm - d/MM/yyyy" value="${user.lastLogin}"/>
                                    </div>

                                    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}">
                                    <input type="hidden" name="id" th:value="${user.id}">
                                    <div class="mt-3">
                                        <label class="form-label style-new-chapter-text">Upload profile picture</label>
                                        <input class="form-control" type="file" name="photos" accept="image/*" required="required"/>
                                        <errors class="error"/>
                                    </div>

                                <div class="d-flex mt-2">
                                    <a href="/" class="btn btn-outline-primary me-1 flex-grow-1">Cancel</a>
                                    <button class="btn btn-primary me-1 flex-grow-7">Add profile picture</button>
                                    <a href="/user/edit/${user.id}" class="btn btn-primary flex-grow-1">Edit User</a>
                                </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>