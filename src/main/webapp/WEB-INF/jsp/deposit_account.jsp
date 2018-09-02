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
    <title>Deposit</title>
    <link rel="stylesheet" href="../css/main.css">
</head>
<body>
<c:set var="account_type" value="deposit" scope="session"/>
<c:set var="lang" value="${empty lang ? 'en' : sessionScope.lang}" scope="session"/>
<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<fmt:setBundle basename="localization/font" scope="session"/>
<%--<jsp:include page="header.jsp"/>--%>

<header>
    <div>
        <img src="" alt="logo">
    </div>
    <div>
        <h1>Deposit Account</h1>
    </div>
    <div>
        <div>User: ${user.getName()}</div>
        <div><a href="/controller?command=sign_out">Sign Out</a></div>
        <div><a href="/controller?command=user_cabinet_page">to Cabinet</a></div>
    </div>
</header>

<div class="account_info">
    <h3>Account Info</h3>
    <table>
        <tr>
            <th>ID</th>
            <th>TYPE</th>
            <th>USER NAME</th>
            <th>USER EMAIL</th>
            <th>BALANCE</th>
            <th>RATE</th>
            <th>OPENING DATE</th>
            <th>CLOSING DATE</th>
        </tr>
        <c:forEach items="${deposit_accounts}" var="item">
            <tr>
                <td>${item.getIdInDb()}</td>
                <td>${item.getType()}</td>
                <td>${item.getUser().getName()}</td>
                <td>${item.getUser().getEmail()}</td>
                <td>${item.getBalance()}</td>
                <td>${item.getRate()}%</td>
                <td>${item.getValidityFrom()}</td>
                <td>${item.getValidityTo()}</td>
                <td>
                    <div>
                        <form action="/controller?command=replenishment&account_id=${item.getIdInDb()}" method="post">
                            <fieldset>
                                <label>
                                    Sum
                                </label>
                                <input type="text" name="sum" placeholder="Enter sum">
                            </fieldset>
                            <fieldset class="signin_action">
                                <input class="btn_fill" type="submit" name="submit" value="Confirm Replenish">
                            </fieldset>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div>
    <h3>Replenishment</h3>
    <form action="/controller?command=replenishment&account_id=${item.getIdInDb()}" method="post">
        <fieldset>
            <label>
                Sum
            </label>
            <input type="text" name="sum" placeholder="Enter sum">
        </fieldset>
        <fieldset class="signin_action">
            <input class="btn_fill" type="submit" name="submit" value="Confirm Replenish">
        </fieldset>
    </form>
</div>

<div>
    <p><b>Request For Opening An Account</b></p>
    <form action="/controller?command=new_account_request" method="post">
        <fieldset>
            <label>
                Rate
            </label>
            <select name="rate" id="rate_id2">
                <option value="5">5%</option>
                <option value="10">10%</option>
                <option value="15">15%</option>
                <option value="20">20%</option>
                <option value="25">25%</option>
                <option value="30">30%</option>
            </select>
        </fieldset>
        <fieldset class="signin_action">
            <input class="btn_fill" type="submit" name="submit" value="New Deposit Account Request">
        </fieldset>
    </form>

</div>

<div class="message">
    <h5>Messages:</h5>
    ${message}
</div>


</body>
</html>
