<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rednek
  Date: 16.08.2018
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin cab</title>
    <link rel="stylesheet" href="../../css/main.css">

</head>
<body>
<header>
    <div>
        <%--<img src="">--%>
    </div>
    <div><h1>Admin Cabinet</h1></div>
    <div>
    <div>Admin: ${admin.getEmail()}</div>
    <a href = "/controller?command = sign_out">Sign Out</a>
    </div>
</header>
<div class="requests">
    <h4>Open new account request</h4>
    <table>
        <tr>
            <th>ID</th>
            <th>TYPE</th>
            <th>USER</th>
            <th>DATE</th>
            <th>RATE</th>
            <th>LIMIT(for credit only)</th>
        </tr>
        <c:forEach items = "${requests}" var="item">
            <tr>
                <td>${item.dbId}</td>
                <td>${item.getAccountType()}</td>
                <td>${item.getUser().getEmail()}</td>
                <td>${item.getDate()}</td>
                <td>${item.getRate()}</td>
                <td>${item.getLimit()}</td>
                <td><a href="/controller?command=accept_request&request_id=${item.getIdInDb()}">
                    <button>Accept</button>
                </a></td>
                <td><a href="/controller?command=decline_request&request_id=${item.getIdInDb()}">
                    <button>Decline</button>
                </a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class ="approved_account">
    <h3>Approved accounts</h3>
    <table><tr>
        <th>ID</th>
        <th>TYPE</th>
        <th>USER NAME</th>
        <th>USER EMAIL</th>
        <th>BALANCE</th>
        <th>RATE</th>
        <th>OPENING DATE</th>
        <th>CLOSING DATE</th>
    </tr>
        <c:forEach items="${approved_accounts}" var="item">
            <tr>
                <td>${item.getDbId}</td>
                <td>${item.getType()}</td>
                <td>${item.getUser().getName()}</td>
                <td>${item.getUser().getEmail()}</td>
                <td>${item.getBalance()}</td>
                <td>${item.getRate()}%</td>
                <td>${item.getValidityStart()}</td>
                <td>${item.getValidityEnd()}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="tramsactions">
    <h3>Transactions</h3>
    <div class="block">
        <table>
            <tr>
            <th>ID</th>
            <th>TIME STAMP</th>
            <th>AMOUNT</th>
            <th>SENDER ACCOUNT</th>
            <th>RECIPIENT ACCOUNT</th>
            </tr>
            <c:forEach items="${all_transactions}" var="item">
                <tr>
                    <td>${item.getDbId()}</td>
                    <td>${item.getTime()}</td>
                    <td>${item.getAmount()}</td>
                    <td>${item.getSender().getDbId()}</td>
                    <td>${item.getRecipient().getDbId()}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<div class="message">
    <h4>Messages:</h4>
    ${message}
</div>
</body>
</html>
