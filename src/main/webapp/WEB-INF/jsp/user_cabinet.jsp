<%--
  Created by IntelliJ IDEA.
  User: Rednek
  Date: 16.08.2018
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cabinet</title>
    <link rel="stylesheet" href="../../css/main.css">
</head>
<body>
<c:set var="lang" value="${empty lang ? 'en' : sessionScope.lang}" scope="session"/>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="localization/font" scope="session"/>
<jsp:include page="header.jsp"/>

<div class="footer">
    <div class="accounts">
        <h3>ACCOUNTS</h3>
        <a href="/controller?command=deposit_account">Deposit</a>
        <a href="/controller?command=credit_account">Credit</a>
    </div>

    <div class="message">
        <h4>Messages</h4>
        ${message}
    </div>
</div>
</body>
</html>
