<%--
  Created by IntelliJ IDEA.
  User: Rednek
  Date: 16.08.2018
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Montserrat:500,600" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700,800&amp;subset=cyrillic"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <link rel="stylesheet" type="text/css" href="../css/reset.css">
    <link rel="stylesheet" type="text/css" href="../css/catalog.css">
    <meta charset="UTF-8">
    <title><fmt:message key="creditAccountHeader"/></title>
</head>
<c:set var="current_page" value="credit_account.jsp" scope="session"/>
<c:set var="lang" value="${empty lang ? 'en' : sessionScope.lang}" scope="session"/>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="localization/font" scope="session"/>

<body>
<header>
    <div class="head">
        <div class="flex-head">
            <a href="catalog.html" class="logo"></a>
            <p class="head_title"><fmt:message key="creditAccountHeader"/></p>
            <div class="dropdown">
                <button class="dropbtn">${user.getName()}</button>
                <div class="triangle-right"></div>
                <div class="dropdown-content">
                    <a href="/controller?command=CHANGE_LANG&lang=ru">Русский</a>
                    <a href="/controller?command=CHANGE_LANG&lang=en">English</a>
                    <a href="/controller?command=user_cabinet_page">to Cabinet</a>
                    <a href="/controller?command=sign_out">Sign Out</a>
                </div>
            </div>
        </div>

    </div>

</header>

<main>
    <div class="catalog_section">
        <div class="flex-catalog">
            <div class="card_catalog-1">
                <p class="card_title"><fmt:message key="accountInfo"/> </p>
                <table>
                    <tr>
                        <th><fmt:message key="thId"/> </th>
                        <th><fmt:message key="thType"/> </th>
                        <th><fmt:message key="thUserName"/> </th>
                        <th><fmt:message key="thUserEmail"/> </th>
                        <th><fmt:message key="thBalance"/> </th>
                        <th><fmt:message key="thRate"/> </th>
                        <th><fmt:message key="thOpeningDate"/> </th>
                        <th><fmt:message key="thClosingDate"/> </th>
                    </tr>
                    <c:forEach items="${credit_accounts}" var="item">
                        <tr>
                            <td>${item.getIdInDb()}</td>
                            <td>${item.getType()}</td>
                            <td>${item.getUser().getName()}</td>
                            <td>${item.getUser().getEmail()}</td>
                            <td>${item.getBalance()}</td>
                            <td>${item.getRate()}%</td>
                            <td>${item.getValidityFrom()}</td>
                            <td>${item.getValidityTo()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="card_catalog-2">
                <p class="card_title"><fmt:message key="thMoneyTranfer"/> </p>
                <form action="/controller?command=transfer&account_id=${item.getIdInDb()}" method="post">
                    <input type="text" name="sender" placeholder=<fmt:message key="formTransferPlaceholderSender"/> >
                    <input type="text" name="recipient" placeholder=<fmt:message key="formTransferPlaceholderRecipient"/> >
                    <input type="text" name="sum" placeholder=<fmt:message key="formTransferPlaceholderSum"/> >
                    <input type="submit" value=<fmt:message key="formTransferPlaceholderButton"/> >
                </form>
            </div>

            <div class="card_catalog-3">
                <p class="card_title"><fmt:message key="formReqNewCreditAccountHeader"/> </p>
                <form action="/controller?command=new_account_request" method="post">
                    <fieldset>
                        <label>
                            <fmt:message key="formReqNewCreditAccountRate"/>
                        </label>
                        <select name="rate" id="rate_id2">
                            <option value="5">5%</option>
                            <option value="10">10%</option>
                            <option value="15">15%</option>
                            <option value="20">20%</option>
                            <option value="25">25%</option>
                            <option value="30">30%</option>
                        </select>
                        <label>
                            <fmt:message key="formReqNewCreditAccountCreditLimit"/>
                        </label>
                        <select name="limit" id="limit_id2">
                            <option value="1000">1000</option>
                            <option value="5000">5000</option>
                            <option value="10000">10000</option>
                            <option value="50000">50000</option>
                            <option value="100000">100000</option>
                        </select>
                    </fieldset>
                    <fieldset class="signin_action">
                        <input class="btn_fill" type="submit" name="submit" value=<fmt:message key="formReqNewCreditAccountButton"/> >
                    </fieldset>
                </form>
            </div>

            <div class="card_catalog-4">
                <p class="card_title"><fmt:message key="messages"/> </p>
                ${message}
            </div>


        </div>
    </div>

</main>

</body>
</html>