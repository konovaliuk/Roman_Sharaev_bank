<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/WEB-INF/tag.tld" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:500,600" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800&amp;subset=cyrillic"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="css/index_style.css">
    <meta charset="UTF-8">
    <title>Iron Bank</title>
</head>
<body>
<c:set var="current_page" value="index.jsp" scope="session"/>
<c:set var ="lang" value ="${empty lang ? 'en' :sessionScope.lang}" scope="session"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="localization/font" scope="session"/>
<header>
    <div class="head">
        <div class ="flex-header">
        <a href = "index.jsp" class="logo"></a>
        <div class="drop">
            <button class="dropbtn"><fmt:message key="langButton"/> </button>
            <div class="dropdown-content">
                <a href="/controller?command=CHANGE_LANG&lang=ru">Русский</a>
                <a href="/controller?command=CHANGE_LANG&lang=en">English</a>
            </div>
        </div>
      </div>
    </div>
</header>
<main>
    <div class="container">
        <div class="text_btn">
            <h1 class="title_1"><fmt:message key = "indexName"/></h1>
            <h2 class="title_2"><fmt:message key="indexSlogan"/></h2>
            <form action="/controller" method="post">
                <input type="hidden" name="command" value="user_sign_in_page"/>
                <div class="btn_border">
                <input class="btn_fill" type="submit" value="<fmt:message key="enter"/>">
                </div>
            </form>
            <form action="/controller" method="post">
                <input type="hidden" name="command" value="user_sign_up_page"/>
                <input class="btn_fill" type="submit" value="<fmt:message key="registration"/>">
            </form>
                <form action="/controller" method="post">
                <input type="hidden" name="command" value="admin_sign_in">
                <input class="btn_fill" type="submit" value="<fmt:message key="adminEnter"/>">
                </form>
        </div>
    </div>
    </div>
    </div>
</main>
<%--<h2 align="center"><ct:today format="MMMM dd, yyyy"/></h2>--%>
</body>
</html>
