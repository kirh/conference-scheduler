<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">
<c:set var="title" value="Error"/>
<%@include file="fragment/header.jspf" %>

<section class="text-center">
    <span class="error">
        <c:choose>
            <c:when test="${exception != null}">
                <fmt:message key="error.internal"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="${error}"/>
            </c:otherwise>
        </c:choose>
    </span>
</section>
<%@include file="fragment/footer.jspf" %>
</fmt:bundle>
