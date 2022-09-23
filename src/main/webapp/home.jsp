<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="text.home"/></title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md py-3">
    <div class="container"><a class="navbar-brand d-flex align-items-center" href="#">
        <span><fmt:message key="text.brand"/></span></a>
        <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navcol-2"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div id="nav-col-2" class="collapse navbar-collapse">
            <div class="btn-group col-md-5 w-auto ms-auto" role="group" aria-label="Button group with nested dropdown">
                <c:if test="${sessionScope.user == null}">
                    <div class="col-md-15 text-end">
                        <a href = "controller?command=loginForm"> <button type="button" class="btn btn-outline-primary"><fmt:message key="text.login"/></button></a>
                        <a href = "controller?command=registrationForm"> <button type="button" class="btn btn-outline-success"><fmt:message key="text.register"/></button></a>
                    </div>
                </c:if>
                <c:if test="${sessionScope.user != null}">
                    <div class="col-md-15 text-end">
                        <a href=><fmt:message key="text.greetings"/>
                                ${sessionScope.user.firstName} ${sessionScope.user.lastName}
                        </a>
                        <a href="controller?command=logout"><button type="button" class="btn btn-outline-danger"><fmt:message key="text.logout"/></button></a>
                    </div>
                </c:if>
                <div class="btn-group btn-group-sm" role="group" style="margin-left: 5px">
                    <button id="btnGroupDrop1" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="logo.globe"/>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <li><a class="dropdown-item" href="controller?command=setLang&locale=ua&pageToProcess=${param.command}">UA</a></li>
                        <li><a class="dropdown-item" href="controller?command=setLang&locale&pageToProcess=${param.command}">ENG</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<hr class="bg-secondary border-2 border-top border-secondary">
<div class="container py-4 py-xl-5">
    <div class="row mb-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <h2><fmt:message key="text.about.us"/></h2>
            <p class="w-lg-50"><fmt:message key="text.long.text"/></p>
        </div>
    </div>
</div>
<%@ include file="include/footer.jsp" %>

<script src="js/bootstrap.min.js"></script>
</body>

</html>