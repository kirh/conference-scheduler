<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cf" uri="http://conference.by" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="i18n">
<c:set var="title" value="Error"/>
<%@include file="fragment/header.jspf" %>

<section class="text-center">
    <cf:error/>
</section>
<%@include file="fragment/footer.jspf" %>
</fmt:bundle>
