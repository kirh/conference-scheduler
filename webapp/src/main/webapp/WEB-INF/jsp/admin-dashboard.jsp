<%--
  Created by IntelliJ IDEA.
  User: kirh
  Date: 27.12.17
  Time: 14:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Conference scheduler - administrator dashboard</title>
    <link rel="stylesheet" href="static/css/style.css">
</head>
<body>
<%@include file="fragment/header.jspf"%>
<section>
    <h2 class="page-header">administrator dashboard</h2>
    <table>
        <th>Name</th>
        <th>Description</th>
        <tr>
        <tbody>
        <c:forEach items="${conferences}" var="conference">
            <tr>
                <td class="conference-name">${conference.name}</td>
                <td>${conference.desctiption}</td>
                <td>
                    <button class="btn" onclick="openEdit()">edit</button>
                    <button class="btn btn-cancel" onclick="removeConference()">remove</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </tr>
    </table>
    <div class="container-center">
    <button class="btn">add</button>
    </div>
</section>
</body>
</html>
