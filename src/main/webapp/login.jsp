<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
  <title><fmt:message key="text.login"/></title>
  <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md py-3">
  <div class="container"><a class="navbar-brand d-flex align-items-center" href="#"><span
          class="bs-icon-sm bs-icon-rounded bs-icon-semi-white d-flex justify-content-center align-items-center me-2 bs-icon">
        </span><span><fmt:message key="text.brand"/></span></a>
    <div class="collapse navbar-collapse" id="nav-col-1">
      <div class="btn-group col-md-5 w-auto ms-auto" role="group" aria-label="Button group with nested dropdown">
        <button id="btnGroupDrop1" type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
          <fmt:message key="logo.globe"/>
        </button>
        <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
          <li><a class="dropdown-item" href="#">UA</a></li>
          <li><a class="dropdown-item" href="#">ENG</a></li>
        </ul>
      </div>
    </div>
  </div>
</nav>
<div class="container">
  <div class="row">
    <div class="col">
      <section class="position-relative py-4 py-xl-5">
        <div class="container">
          <div class="row d-flex justify-content-center">
            <div class="col-md-6 col-xl-4">
              <div class="card mb-5">
                <div class="card-body d-flex flex-column align-items-center">
                  <form class="text-center" method="post">
                    <div class="mb-3"><input class="form-control" type="email" name="email"
                                             placeholder="Email" minlength="4" maxlength="45"
                                             pattern="([\w-\.]{1,})+@([\w-]+\.)+([\w-]{2,4})$" required="">
                    </div>
                    <div class="mb-3"><input class="form-control" type="password" name="password"
                                             placeholder="Password" required="" minlength="8" maxlength="64"
                                             pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,65}$">
                    </div>
                    <div class="mb-3">
                      <button class="btn btn-success d-block w-100" type="submit"><fmt:message key="text.login"/></button>
                    </div>
                    <p class="text-muted"><fmt:message key="text.forgot.password"/></p>
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