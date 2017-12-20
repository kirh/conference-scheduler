<%--
  Created by IntelliJ IDEA.
  User: kirh
  Date: 27.12.17
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Conference scheduler - sign up</title>
    <link rel="stylesheet" href="static/css/style.css">
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="username" var="username"/>
    <fmt:message bundle="${loc}" key="password" var="password"/>
    <fmt:message bundle="${loc}" key="signin" var="sign_in"/>
    <fmt:message bundle="${loc}" key="signup" var="sign_up"/>
</head>
<body>
<%@include file="WEB-INF/jsp/fragment/header.jspf"%>
<form class="clearfix" action="/do?action=login" method="POST">
    <label for="username-input">${username}:</label>
    <input class="form-control" id="username-input" type="text" name="login" minlength="4" maxlength="16" pattern="\w+" required/>
    <label for="password-input">${password}:</label>
    <input class="form-control" type="password" id="password-input" name="password" minlength="5" required/>
    <a href="signup.jsp"><button type="button" class="btn btn-cancel left">${sign_up}</button></a>
    <input class="btn right" type="submit" value="${sign_in}" />
</form>
</body>
</html>
