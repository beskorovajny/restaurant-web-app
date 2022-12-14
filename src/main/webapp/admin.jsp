<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" uri="WEB-INF/tld/customDateTimeTag.tld" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>
        <fmt:message key="text.admin.panel"/></title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md py-3" aria-label="header">
    <div class="container"><a class="navbar-brand d-flex align-items-center">
        <span><custom:today/></span></a>
        <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#nav-col-2"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div id="nav-col-2" class="collapse navbar-collapse">
            <div class="btn-group col-md-5 w-auto ms-auto" role="group" aria-label="Button group with nested dropdown">

                <div class="col-md-15 text-center" style="margin: 10px">
                    <h6><fmt:message key="text.greetings"/>
                        ${sessionScope.user.firstName} ${sessionScope.user.lastName}
                    </h6>
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
                               href="controller?command=setLang&locale=ua&pageToProcess=${param.command}&page=${param.page}">UA</a>
                        </li>
                        <li><a class="dropdown-item"
                               href="controller?command=setLang&locale&pageToProcess=${param.command}&page=${param.page}">ENG</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<hr class="bg-secondary border-2 border-top border-secondary">
<div class="container py-4 py-xl-5">
    <ul class="nav nav-pills nav-justified">
        <li class="nav-item">
            <a class="nav-link active" aria-current="page"><fmt:message key="text.receipts"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="controller?command=dishes"><fmt:message key="text.dishes"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="controller?command=users"><fmt:message key="text.users"/></a>
        </li>
    </ul>
    <hr class="bg-secondary border-2 border-top border-secondary">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col"><fmt:message key="text.date.created"/></th>
            <th scope="col"><fmt:message key="text.total.price"/></th>
            <th scope="col"><fmt:message key="text.status"/></th>
            <th scope="col"><fmt:message key="text.user"/> ID</th>
            <th scope="col"><fmt:message key="text.contacts"/> ID</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="receipt" items="${requestScope.receipts}">
            <tr>
                <td>${receipt.id}</td>
                <td><fmt:parseDate value="${receipt.dateCreated}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"
                                   type="date"/>
                    <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>${receipt.totalPrice}</td>
                <td>
                    <c:choose>
                        <c:when test="${receipt.status.id == 1}">
                            <fmt:message key="text.new" var="statusNew"/>
                            ${statusNew}
                        </c:when>
                        <c:when test="${receipt.status.id == 2}">
                            <fmt:message key="text.cooking" var="cooking"/>
                            ${cooking}
                        </c:when>
                        <c:when test="${receipt.status.id == 3}">
                            <fmt:message key="text.delivery" var="delivery"/>
                            ${delivery}
                        </c:when>
                        <c:when test="${receipt.status.id == 4}">
                            <fmt:message key="text.completed" var="completed"/>
                            ${completed}
                        </c:when>
                    </c:choose>
                </td>
                <td>${receipt.contactsId}</td>
                <td>${receipt.customerId}</td>
                <td>
                    <button type="button" class="btn btn-outline-secondary"
                            onclick="window.location='controller?command=receipt_details&receiptId=${receipt.id}'">
                        <fmt:message key="text.details"/></button>
                    <button type="button" class="btn btn-outline-warning"
                            onclick="window.location='controller?command=update_receipt_status&receiptId=${receipt.id}&page=${param.page}'">
                        <fmt:message key="text.edit"/></button>
                    <button type="button" class="btn btn-outline-danger"
                            onclick="window.location='controller?command=remove_receipt&receiptId=${receipt.id}'">
                        <fmt:message key="text.remove"/></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav>
        <ul class="pagination justify-content-center">
            <c:if test="${param.page-1 >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=${param.command}&page=${param.page-1}"><fmt:message
                        key="text.previous"/></a>
                </li>
            </c:if>

            <c:forEach var="page" items="${requestScope.pages}">

                <li class="page-item"><a class="page-link"
                                         href="controller?command=${param.command}&page=${page}">${page}</a>
                </li>

            </c:forEach>
            <c:set var="size" scope="page" value="${requestScope.pages}"/>

            <c:if test="${param.page+1 <= size.size()}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=${param.command}&page=${param.page+1}"><fmt:message
                        key="text.next"/></a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
<script src="js/bootstrap.min.js"></script>
</body>
</html>