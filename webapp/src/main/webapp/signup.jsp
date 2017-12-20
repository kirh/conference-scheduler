<%--
  Created by IntelliJ IDEA.
  User: kirh
  Date: 29.12.17
  Time: 15:36
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
    <fmt:message bundle="${loc}" key="username.field.placeholder" var="username_placeholder"/>
    <fmt:message bundle="${loc}" key="password" var="password"/>
    <fmt:message bundle="${loc}" key="password.field.placeholder" var="password_placeholder"/>
    <fmt:message bundle="${loc}" key="email" var="email"/>
    <fmt:message bundle="${loc}" key="email.field.placeholder" var="email_placeholder"/>
    <fmt:message bundle="${loc}" key="firstname" var="firstname"/>
    <fmt:message bundle="${loc}" key="lastname" var="lastname"/>
    <fmt:message bundle="${loc}" key="participant" var="participant"/>
    <fmt:message bundle="${loc}" key="administrator" var="administrator"/>
    <fmt:message bundle="${loc}" key="signup" var="sign_up"/>
    <fmt:message bundle="${loc}" key="cancel" var="cancel"/>
</head>
<body>
<%@ include file="WEB-INF/jsp/fragment/header.jspf"%>
<form class="clearfix" action="/do?action=signup" method="POST">
    <label for="username-input">${username}:</label>
    <input class="form-control" type="text" name="username" id="username-input" placeholder="${username_placeholder}"
           minlength="4" maxlength="16"  required/>
    <label for="password-input">${password}:</label>
    <input class="form-control" type="password" name="password" id="password-input" placeholder="Enter password" minlength="8" required/>
    <input class="form-control" type="password" placeholder="${password_placeholder}" id="password-repeat-input"
           oninput="checkpassword()"
           minlength="8" required/>
    <label for="email-input">${email}:</label>
    <input class="form-control" type="email" name="email" id="email-input" placeholder="${email_placeholder}" required/>
    <label for="first-name-input">${firstname}:</label>
    <input class="form-control" type="text" name="firstname" id="first-name-input" placeholder="${firstname}" required>
    <label for="last-name-input">${lastname}:</label>
    <input class="form-control" type="text" name="lastname" id="last-name-input"  placeholder="${lastname}" required/>
    <input type="radio" name="role" value="participant" checked/>${participant}<br>
    <input type="radio" name="role" value="admin" />${administrator}<br>
    <a href="login.html"><button type="button" class="btn btn-cancel left">${cancel}</button></a>
    <input class="btn right" type="submit" value="${sign_up}"/>
</form>
</body>
</html>
