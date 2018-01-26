<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

<%@include file="fragment/header.jspf" %>

<fmt:message var="locCreateConference" key="conference.form.create"/>
<fmt:message var="locEditConference" key="conference.form.edit"/>
<fmt:message var="locDescription" key="description"/>
<fmt:message var="locName" key="name"/>
<fmt:message var="locAddress" key="conference.form.address"/>
<fmt:message var="locDate" key="date"/>
<fmt:message var="locCancel" key="cancel"/>
<fmt:message var="locSave" key="save"/>

<c:url value="/conference" var="process">
    <c:param name="action" value="process"/>
</c:url>

<section>
    <h2 class="page-header">${empty conference ? locCreateConference : locEditConference}</h2>
    <form class="clearfix" method="POST" action="${process}">
        <c:if test="${not empty conference}">
            <input type="hidden" name="id" value="${fn:escapeXml(conference.id)}"/>
        </c:if>
        <label for="i-c-name">${locName}:</label>
        <input type="text" class="form-control" id="i-c-name" name="name" minlength="5"
               value="${fn:escapeXml(conference.name)}" pattern="(\S+\s?)*" required/>
        <label for="i-c-description">${locDescription}:</label>
        <textarea class="form-control i-description" id="i-c-description" name="description" minlength="20" required>
            ${not empty conference ? fn:escapeXml(conference.description) : ""}
        </textarea>
        <label for="i-c-address">${locAddress}:</label>
        <input type="text" class="form-control" id="i-c-address" name="address" pattern="(\S+\s?)*" minlength="5"
               value="${fn:escapeXml(conference.address)}" required/>
        <label for="i-c-date">${locDate}:</label>
        <input type="date" id="i-c-date" class="form-control" name="datetime"
               value="${fn:escapeXml(conference.date)}" required/>
        <button type="button" class="btn btn-cancel left" onclick="goToPreviousPage()">${locCancel}</button>
        <input type="submit" class="btn right" value="${locSave}"/>
    </form>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>