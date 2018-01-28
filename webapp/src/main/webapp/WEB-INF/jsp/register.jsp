<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="i18n">

    <%@include file="fragment/header.jspf" %>

    <fmt:message key="username" var="locUsername"/>
    <fmt:message key="username.field.placeholder" var="locUsernamePlaceholder"/>
    <fmt:message key="password" var="locPassword"/>
    <fmt:message key="password.field.placeholder" var="locPasswordPlaceholder"/>
    <fmt:message key="password.field.repeatplaceholder" var="locPasswordRepeatPlaceholder"/>
    <fmt:message key="email" var="locEmail"/>
    <fmt:message key="email.field.placeholder" var="locEmailPlaceholder"/>
    <fmt:message key="birthday" var="locBirthday"/>
    <fmt:message key="firstname" var="locFirstName"/>
    <fmt:message key="lastname" var="locLastName"/>
    <fmt:message key="participant" var="locParticipant"/>
    <fmt:message key="administrator" var="locAdministrator"/>
    <fmt:message key="signup" var="locSignUp"/>
    <fmt:message key="cancel" var="locCancel"/>
    <fmt:message key="registration" var="locRegistration"/>
    <fmt:message key="username.title" var="locUsernameTitle"/>
    <fmt:message key="password.title" var="locPasswordTitle"/>
    <fmt:message key="title.nospaces" var="locNoSpaces"/>
    <c:url value="/register" var="process">
        <c:param name="action" value="process"/>
    </c:url>
    <section>
        <h2>${locRegistration}</h2>
        <<c:if test="${not empty error}">
            <span class="error">
                    <fmt:message key="${error}"/>
            </span>
    </c:if>
        <form class="clearfix" method="POST" action="${process}">
            <label for="username-input">${locUsername}:</label>
            <input class="form-control" type="text" name="username" id="username-input"
                   placeholder="${locUsernamePlaceholder}"
                   minlength="5" maxlength="16"
                   pattern="[a-zA-Z]\w+"
                   title="${locUsernameTitle}"
                   required/>

            <label for="password-input">${locPassword}:</label>
            <input class="form-control" type="password" name="password" id="password-input"
                   placeholder="${locPasswordPlaceholder}"
                   minlength="5"
                   pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
                   maxlength="16"
                   title="${locPasswordTitle}"
                   required/>
            <input class="form-control" type="password" placeholder="${locPasswordRepeatPlaceholder}"
                   id="password-repeat-input"
                   oninput="checkpassword()"
                   minlength="5" required/>

            <label for="email-input">${locEmail}:</label>
            <input class="form-control" id="email-input" type="email" name="email" placeholder="Enter Email" required>

            <label for="first-name-input">${locFirstName}:</label>
            <input class="form-control" type="text" name="firstName" id="first-name-input" pattern="\S+" minlength="2"
                   maxlength="20"
                   placeholder="${locFirstName}"
                   title="${locNoSpaces}"
                   required>

            <label for="last-name-input">${locLastName}:</label>
            <input class="form-control" type="text" name="lastName" id="last-name-input" placeholder="${locLastName}"
                   pattern="\S+" minlength="2" maxlength="20" title="${locNoSpaces}" required/>

            <input type="radio" name="admin" value="false" checked/>${locParticipant}
            <br>
            <input type="radio" name="admin" value="true"/>${locAdministrator}
            <br>

            <button type="button" onclick="goToPreviousPage()" class="btn btn-cancel left">${locCancel}</button>

            <input class="btn right" type="submit" value="${locSignUp}"/>
        </form>
    </section>

    <%@include file="fragment/footer.jspf" %>
</fmt:bundle>