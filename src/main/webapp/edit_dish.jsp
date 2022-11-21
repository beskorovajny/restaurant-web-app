<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
  <meta charset="UTF-8" http-equiv="Content-Type" name="viewport"
        content="text/html, width=device-width, initial-scale=1.0, shrink-to-fit=no"/>
  <title><fmt:message key="text.edit.dish"/></title>
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
        <div class="btn-group btn-group-sm" role="group" style="margin-left: 5px">
          <button id="btnGroupDrop1" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown"
                  aria-expanded="false">
            <fmt:message key="logo.globe"/>
          </button>
        </div>
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
                  <form class="text-center" action="controller" method="post">
                    <input hidden name="command" value="edit_dish"/>
                    <input type="hidden" name="dishId" value="${requestScope.dish.id}">
                    <div style="margin-bottom: 16px;"><input class="form-control" type="text"
                                                             placeholder="${requestScope.dish.title}"
                                                             name="title"
                                                             pattern="(?=.{1,45}$)[\p{L}\s\d*]+$"
                                                             minlength="2"
                                                             maxlength="45"/></div>
                    <div style="margin-bottom: 16px;"><input class="form-control" type="text"
                                                             placeholder="${requestScope.dish.description}"
                                                             name="description" minlength="2"
                                                             pattern="(?=.{1,255}$)[\p{L}\s\d*]+$"
                                                             maxlength="255"/></div>
                    <div style="margin-bottom: 16px;"><input class="form-control" type="number"
                                                             name="price"
                                                             placeholder="price=${requestScope.dish.price}"
                                                             minlength="1"
                                                             step=".01"
                                                             pattern="^\d+(.\d{1,2})?$"/>
                    </div>
                    <div class="mb-3"><input class="form-control" type="number"
                                             name="weight"
                                             placeholder="weight=${requestScope.dish.weight}"
                                             step=".01"
                                             pattern="^\d+$"/>
                    </div>
                    <div style="margin-bottom: 16px;"><input class="form-control" type="number"
                                                             name="cooking"
                                                             placeholder="cooking=${requestScope.dish.cooking}"
                                                             pattern="^\d+$"/>
                    </div>
                    <select name="category" style="margin-bottom: 10px">
                      <option disabled><fmt:message key="text.category"/></option>
                      <option value="salad"><fmt:message key="text.salad"/></option>
                      <option value="pizza"><fmt:message key="text.pizza"/></option>
                      <option value="appetizer"><fmt:message key="text.appetizer"/></option>
                      <option value="drink"><fmt:message key="text.drink"/></option>
                    </select>
                    <div class="mb-3">
                      <button class="btn btn-success d-block w-100" type="submit"
                              value=""><fmt:message key="text.update"/>
                      </button>
                    </div>
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
<%@ include file="include/footer.jspf" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>