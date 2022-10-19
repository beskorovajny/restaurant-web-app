<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">

<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="text.receipt.details"/></title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md py-3">
    <div class="container"><a class="navbar-brand d-flex align-items-center" href="controller?command?=home">
        <span><fmt:message key="text.brand"/></span></a>
        <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#nav-col-2"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div id="nav-col-2" class="collapse navbar-collapse">
            <div class="btn-group col-md-5 w-auto ms-auto" role="group"
                 aria-label="Button group with nested dropdown">
                <div class="btn-group btn-group-sm" role="group" style="margin-left: 5px">
                    <button id="btnGroupDrop1" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown"
                            aria-expanded="false">
                        <fmt:message key="logo.globe"/>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <li><a class="dropdown-item"
                               href="controller?command=setLang&locale=ua&pageToProcess=${param.command}">UA</a>
                        </li>
                        <li><a class="dropdown-item"
                               href="controller?command=setLang&locale&pageToProcess=${param.command}">ENG</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>
<hr class="bg-secondary border-2 border-top border-secondary">
<div class="container">
    <div class="row">
        <div class="col-md-4 order-md-2 mb-4">
            <h4 class="d-flex justify-content-between align-items-center mb-3">
                <span class="text-muted"><fmt:message key="text.ordered.dishes"/></span>
                <span class="badge badge-secondary badge-pill">3</span>
            </h4>
            <ul class="list-group mb-3">
                <c:forEach var="entry" items="${sessionScope.cart}">
                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                        <div>
                            <h6 class="my-0"><c:out value="${entry.key.title}"/></h6>
                            <small class="text-muted"><c:out value="${entry.key.description}"/></small>
                        </div>
                        <span class="text-muted">
                                    <small class="my-0">pcs: <c:out value="${entry.value}"/></small>
                                    Price: <c:out value="${entry.key.price * entry.value}"/>
                                </span>
                    </li>
                </c:forEach>
                <li class="list-group-item d-flex justify-content-between">
                    <span><fmt:message key="text.total.price"/></span>
                    <strong>$20</strong>
                </li>
            </ul>
        </div>
        <div class="col-lg-8">
            <div class="card mb-4">
                <div class="card-body">
                    <h5><fmt:message key="text.customer.info"/></h5>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0"><fmt:message key="text.full.name"/></p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0">Johnatan Smith</p>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0">Email</p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0">example@example.com</p>
                        </div>
                    </div>
                    <hr>
                    <h5><fmt:message key="text.contact.info"/></h5>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0"><fmt:message key="text.country"/></p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0">USA</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0"><fmt:message key="text.city"/></p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0">San Francisco, CA</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0"><fmt:message key="text.street"/></p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0">Bay Area</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0"><fmt:message key="text.building"/></p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0">114/2</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0"><fmt:message key="text.phone"/></p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0">(097) 234-5678</p>
                        </div>
                    </div>
                    <hr>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="include/footer.jspf" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>