<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">

<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="logo.cart"/></title>
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
<c:choose>
    <c:when test="${sessionScope.cart == null || sessionScope.cart.isEmpty()}">
        <div class="container py-4 py-xl-5">
            <div class="row mb-5">
                <div class="col-md-8 col-xl-6 text-center mx-auto">
                    <h4 class="mb-3">Your cart is empty</h4>
                    <div class="col-md-15 text-center" style="margin-right: 10px;">
                        <a href="controller?command=menu" style="text-decoration:none;">
                            <button type="button" class="btn btn-outline-success">
                                <fmt:message key="text.menu"/>
                            </button>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${sessionScope.cart != null && !sessionScope.cart.isEmpty()}">
        <div class="container">
            <div class="row">
                <div class="col-md-4 order-md-2 mb-4">
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-muted">Your cart</span>
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
                            <span>Total ($)</span>
                            <c:set var="total" value="${0}"/>
                            <c:forEach var="entry" items="${sessionScope.cart}">
                                <c:set var="total" value="${total + (entry.key.price * entry.value)}"/>
                            </c:forEach>
                            <strong><c:out value="${total}"/></strong>
                        </li>
                    </ul>
                </div>
                <div class="col-md-8 order-md-1">
                    <h4 class="mb-3">Billing address and contacts</h4>
                    <form class="needs-validation" action="controller" method="post" novalidate>
                        <input hidden name="command" value="checkout"/>
                        <div class="mb-3">
                            <label for="country"></label>
                            <input type="text" class="form-control" id="country" name="country" placeholder="Country"
                                   required pattern="^(?=.{3,45}$)[\p{L}+(\s+\p{L}+)]+$">
                        </div>
                        <div class="mb-3">
                            <label for="city"></label>
                            <input type="text" class="form-control" id="city" name="city" placeholder="City" required
                                   pattern="^(?=.{3,45}$)[\p{L}+(\s+\p{L}+)]+$">
                            <div class="invalid-feedback">
                                Please enter your city info.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="street"></label>
                            <input type="text" class="form-control" id="street" name="Street" placeholder="street"
                                   required pattern="^(?=.{3,45}$)[\p{L}+(\s+\p{L}+)]+$">
                            <div class="invalid-feedback">
                                Please enter your street info.
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="building"></label>
                            <input type="text" class="form-control" id="building" placeholder="Building" required
                                   pattern="^(?=.{3,45}$)[\p{L}+(\s+\p{L}+)]+$">
                            <div class="invalid-feedback">
                                Please enter your building info.
                            </div>
                        </div>
                        <h4 class="mb-3">Payment details</h4>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="cc-number"></label>
                                <input type="text" class="form-control" id="cc-number"
                                       placeholder="Credit card number" required>
                                <div class="invalid-feedback">
                                    Credit card number is required
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3 mb-3">
                                <label for="cc-expiration"></label>
                                <input type="text" class="form-control" id="cc-expiration" placeholder="Expiration"
                                       required>
                                <div class="invalid-feedback">
                                    Expiration date required
                                </div>
                            </div>
                            <div class="col-md-3 mb-3">
                                <label for="cc-cvv"></label>
                                <input type="text" class="form-control" id="cc-cvv" placeholder="CVV" required>
                                <div class="invalid-feedback">
                                    Security code required
                                </div>
                            </div>
                        </div>
                        <hr class="mb-4">
                        <button class="btn btn-primary btn-lg btn-block" type="submit">Checkout</button>
                    </form>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>
<%@ include file="include/footer.jspf" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>