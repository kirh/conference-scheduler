<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmtL" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="i18n">

<%@include file="fragment/header.jspf" %>

<fmt:message var="locCreateSection" key="section.create"/>
<fmt:message var="locTopic" key="section.topic"/>
<fmt:message var="locCancel" key="cancel"/>
<fmt:message var="locSave" key="save"/>

<c:url value="/section" var="process">
    <c:param name="action" value="process"/>
</c:url>

<section>
    <h2 class="page-header">${locCreateSection}</h2>
    <form class="clearfix" method="POST" action="${process}">
        <c:if test="${not empty section}">
            <input type="hidden" name="id" value="${fn:escapeXml(section.id)}">
        </c:if>
        <label for="i-s-topic">${locTopic}:</label>
        <input type="text" class="form-control" id="i-s-topic" name="topic" value="${fn:escapeXml(section.topic)}"
               minlength="5" pattern="(\S+\s?)*" required/>
        <input type="hidden" class="form-control" name="conferenceId" value="${empty section
        ? fn:escapeXml(param.conferenceId) : section.conferenceId}" required/>
        <button type="button" class="btn btn-cancel left" onclick="goToPreviousPage()">${locCancel}</button>
        <input type="submit" class="btn right" value="${locSave}"/>
    </form>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>