<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="../../css/reset.css">
    <link rel="stylesheet" type="text/css" href="../../css/main.css">
    <link rel="stylesheet" type="text/css" href = "../../css/main.css">

</head>
<body>

<c:set var="current_page" value="header.jsp" scope="session"/>
<c:set var ="lang" value ="${empty lang ? 'en' :sessionScope.lang}" scope="session"/>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="localization/font" scope="session"/>
<div class="logo">
    <form action="/controller" method="post"></form>
    <a href="/controller?command=index.jsp">
        <a href = "/controller?command=index.jsp">Home</a>
        <%--<img src="http://www.samefacts.com/wp-content/uploads/2015/07/Iron-Bank-logo.png" alt="logo">--%>
    </a>
</div>
<div class="title">
</div>

<div class="dropdown">
    <button class="dropbtn">${user.getName()}</button>
    <div class="triangle-right"></div>
    <div class="dropdown-content">
        <a href="/controller?command=sign_out">Sign Out</a>
        <a href="/?command=change_lang&lang=en"> English </a>
        <a href="/?command=change_lang&lang=ru"> Русский </a>
    </div>
</div>
</body>
</html>
