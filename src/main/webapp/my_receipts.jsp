<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>My receipts</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md py-3">
    <div class="container"><a class="navbar-brand d-flex align-items-center" href="controller?command=home">
        <span><fmt:message key="text.brand"/></span></a>
        <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#nav-col-2"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div id="nav-col-2" class="collapse navbar-collapse">
            <div class="btn-group col-md-5 w-auto ms-auto" role="group" aria-label="Button group with nested dropdown">
                    <div class="col-md-15 text-center" style="margin: 10px">
                        <h6><fmt:message key="text.greetings"/>
                                ${sessionScope.user.firstName} ${sessionScope.user.lastName}
                        </h6>
                    </div>
                    <div class="col-md-15 text-end" style="margin-right: 10px;">
                        <a href="controller?command=menu" style="text-decoration:none;">
                            <button type="button" class="btn btn-outline-success">
                                Catalogue</button>
                        </a>
                    </div>
                    <div class="col-md-15 text-end">
                        <a href="controller?command=logout" style="text-decoration:none;">
                            <button type="button" class="btn btn-outline-warning">
                                <fmt:message key="text.logout"/></button>
                        </a>
                    </div>
                <div class="btn-group btn-group-sm" role="group" style="margin-left: 5px">
                    <button id="btnGroupDrop1" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown"
                            aria-expanded="false">
                        <fmt:message key="logo.globe"/>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <li><a class="dropdown-item"
                               href="controller?command=setLang&locale=ua&pageToProcess=${param.command}">UA</a></li>
                        <li><a class="dropdown-item"
                               href="controller?command=setLang&locale&pageToProcess=${param.command}">ENG</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>
<hr class="bg-secondary border-2 border-top border-secondary">
<div class="container py-4 py-xl-5">
    <hr class="bg-secondary border-2 border-top border-secondary">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Date created</th>
            <th scope="col">Discount</th>
            <th scope="col">Status</th>
            <th scope="col">Address</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="receipt" items="${receipts}">
            <tr>
                <td><c:out value="${receipt.id}"/>
                </td>
                <td><c:out value="${receipt.dateCreated}"/>
                </td>
                <td><c:out value="${receipt.discount}"/>
                </td>
                <td><c:out value="${receipt.status}"/>
                </td>
                <td><c:out value="${receipt.address}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav>
        <ul class="pagination justify-content-center">
            <c:if test="${param.page-1 >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=${param.command}&page=${param.page-1}">Previous</a>
                </li>
            </c:if>

            <c:forEach var="page" items="${pages}">

                <li class="page-item"><a class="page-link"
                                         href="controller?command=${param.command}&page=${page}">${page}</a>
                </li>

            </c:forEach>
            <c:set var="size" scope="page" value="${requestScope.pages}"/>

            <c:if test="${param.page+1 <= size.size()}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=${param.command}&page=${param.page+1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
<%@ include file="include/footer.jsp" %>

<script src="js/bootstrap.min.js"></script>
</body>

</html>