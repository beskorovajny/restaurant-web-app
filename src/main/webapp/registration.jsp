<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messagesBundle"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="text.register"/></title>
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
                <div class="btn-group btn-group-sm" role="group" style="margin-left: 5px">
                    <button id="btnGroupDrop1" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="logo.globe"/>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <li><a class="dropdown-item" href="home?command=setLocale&locale=uk_UA&pageToProcess=${param.command}">UA</a></li>
                        <li><a class="dropdown-item" href="home?command=setLocale&locale&pageToProcess=${param.command}">ENG</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>
<div class="container">
    <div class="col-md-8 col-xl-6 text-center mx-auto">
        <h2><fmt:message key="text.registration.form"/></h2>
    </div>
    <div class="row">
        <div class="col">
            <section class="position-relative py-4 py-xl-5">
                <div class="container">
                    <div class="row d-flex justify-content-center">
                        <div class="col-md-6 col-xl-4">
                            <div class="card mb-5">
                                <div class="card-body d-flex flex-column align-items-center">
                                    <form class="text-center" method="post">
                                        <input hidden name="command" value="register">
                                        <div class="d-lg-flex justify-content-lg-end align-items-lg-start mb-3"><input
                                                class="form-control" type="email" name="email" placeholder="Email"
                                                minlength="3" maxlength="44"
                                                pattern="^(?=.{4,45}$)([\w-\.]{1,})+@([\w-]+\.)+([\w-]{2,4})$"
                                                required=""></div>
                                        <div style="margin-bottom: 16px;"><input class="form-control" type="text"
                                                                                 placeholder="First Name"
                                                                                 name="firstName"
                                                                                 pattern="^[а-яА-яa-zA-z ]{2,45}$"
                                                                                 minlength="1" required=""
                                                                                 maxlength="44"></div>
                                        <div style="margin-bottom: 16px;"><input class="form-control" type="text"
                                                                                 placeholder="Last name"
                                                                                 name="lastName" minlength="1"
                                                                                 pattern="^[а-яА-яa-zA-z ]{2,45}$+"
                                                                                 required="" maxlength="44"></div>
                                        <div style="margin-bottom: 16px;"><input class="form-control" type="text"
                                                                                 name="phoneNumber"
                                                                                 placeholder="Phone number"
                                                                                 maxlength="44" required=""
                                                                                 minlength="3"
                                                                                 pattern="^(?=.{3,45}$)[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\s\./0-9]*$">
                                        </div>
                                        <div class="mb-3"><input class="form-control" type="password" name="password"
                                                                 placeholder="Password" required="" minlength="8"
                                                                 pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,65}$">
                                        </div>
                                        <div class="mb-3">
                                            <button class="btn btn-success d-block w-100" type="submit"
                                                    value=""><fmt:message key="text.register"/>
                                            </button>
                                        </div>
                                        <a class="text-muted" href="login.jsp" style="color: #f700b1;"><fmt:message key="text.account.exists"/> </a>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>
<%@ include file = "include/footer.jsp" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>