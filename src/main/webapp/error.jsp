<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="l10n/messagesBundle"/>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no"/>
    <title>Untitled</title>
</head>

<body>
<div class="container w-100 h-100" style="width: 100%;height: 100%;">
    <div class="col">
        <div class="row">
            <div class="col" style="text-align: center;"><img class="img-fluid" src="${pageContext.request.contextPath}/images/error.png"
                                                              alt="error image"/></div>
        </div>
        <div class="row">
            <div class="col">
                <h1 style="text-align: center;color: #c2c2c2;">IT&#39;S NOT YOU<br/>IT&#39;S ME
                </h1>
                <p class="lead shadow-none"
                   style="text-align: center;color: rgb(194,194,194);font-size: 13px;margin: 34px;">
                    Something went terribly wrong...<br/><a class="text-muted" href="home.jsp" style="color: #f700b1;"><fmt:message key="text.home"/> </a></p>
            </div>
        </div>
    </div>
</div>
</body>

</html>
