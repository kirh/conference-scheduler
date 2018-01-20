<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

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
    <c:url value="/register" var="process">
        <c:param name="action" value="process"/>
    </c:url>
    <section>
        <h2>Registration</h2>
        <form class="clearfix" method="POST" action="${process}">
            <label for="username-input">${locUsername}:</label>
            <input class="form-control" type="title" name="username" id="username-input"
                   placeholder="${locUsernamePlaceholder}"
                   minlength="5" maxlength="16"
                   pattern="[a-zA-Z]\w+"
                   title="Must contain only latin letters, digits and underscore. Begins with latin letter"
                   required/>

            <label for="password-input">${locPassword}:</label>
            <input class="form-control" type="password" name="password" id="password-input"
                   placeholder="${locPasswordPlaceholder}"
                   minlength="6"
                   pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
                   title="Must contain at least one letter in uppercase and lowercase and digit"
                   required/>
            <input class="form-control" type="password" placeholder="${locPasswordRepeatPlaceholder}"
                   id="password-repeat-input"
                   oninput="checkpassword()"
                   minlength="6" required/>

            <label for="birthday-input">${locBirthday}:</label>
            <input class="form-control" type="date" name="birthday" id="birthday-input"
                   onchange="validateBirthday(this)"
                   required>

            <label for="email-input">${locEmail}:</label>
            <input class="form-control" id="email-input" type="email" name="email" placeholder="Enter Email" required>

            <label for="first-name-input">${locFirstName}:</label>
            <input class="form-control" type="title" name="firstName" id="first-name-input"
                   placeholder="${locFirstName}"
                   required>

            <label for="last-name-input">${locLastName}:</label>
            <input class="form-control" type="title" name="lastName" id="last-name-input" placeholder="${locLastName}"
                   required/>

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