<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

<c:set var="title" value="Error"/>
<%@include file="fragment/header.jspf" %>
<fmt:message key="${errorKey}" var="locErrorMessage"/>
<fmt:message var="locInternalError" key="error.internal"/>
<section class="container-center">
    <span class="error">
        <c:choose>
            <c:when test="${not empty exception}">
                ${locInternalError}
            </c:when>
            <c:otherwise>
                ${locErrorMessage}
            </c:otherwise>
        </c:choose>
    </span>
</section>
<%@include file="fragment/footer.jspf" %>
</fmt:bundle>
