<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

    <%@include file="fragment/header.jspf" %>

    <fmt:message key="username" var="locUsername"/>
    <fmt:message key="password" var="locPassword"/>
    <fmt:message key="signin" var="locSignIn"/>
    <fmt:message key="signup" var="locSignUp"/>
    <fmt:message key="login" var="locLogin"/>
    <fmt:message key="username.title" var="locUsernameTitle"/>
    <fmt:message key="password.title" var="locPasswordTitle"/>

    <c:url value="/login" var="process">
        <c:param name="action" value="process"/>
    </c:url>
    <section>
        <h2>Sign in</h2>
        <c:if test="${not empty error}">
            <span class="error">
                    <fmt:message key="${error}"/>
            </span>
        </c:if>
        <form class="clearfix" method="POST" action="${process}">
            <label for="username-input">${locUsername}:</label>
            <input class="form-control" id="username-input" type="text" name="username" minlength="5" maxlength="16"
                   pattern="^[a-zA-Z][^\W_]+$"
                   title="${locUsernameTitle}"
                   required/>
            <label for="password-input">${locPassword}:</label>
            <input class="form-control" type="password" id="password-input" name="password" minlength="6" maxlength="16"
                   pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
                   title="${locPasswordTitle}"
                   required/>
            <c:url value="/register" var="showSignUp"/>
            <a href="${showSignUp}">
                <button type="button" class="btn btn-cancel left">${locSignUp}</button>
            </a>
            <input class="btn right" type="submit" value="${locSignIn}"/>
        </form>
    </section>

    <%@include file="fragment/footer.jspf" %>
</fmt:bundle>

