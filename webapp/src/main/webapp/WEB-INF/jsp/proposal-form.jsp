<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="i18n">

<%@include file="fragment/header.jspf" %>

<fmt:message var="locCreateProposal" key="proposal.form.create"/>
<fmt:message var="locTitle" key="title"/>
<fmt:message var="locDescription" key="description"/>
<fmt:message var="locCancel" key="cancel"/>
<fmt:message var="locSave" key="save"/>


<c:url value="/proposal" var="process">
    <c:param name="action" value="process"/>
</c:url>

<section>
    <h2 class="page-header">${locCreateProposal}</h2>
    <form class="clearfix" method="POST" action="${process}">
        <label for="i-p-title">${locTitle}:</label>
        <input type="text" class="form-control" id="i-p-title" name="title" minlength="5" pattern="(\S+\s?)*" required/>
        <label for="i-p-description">${locDescription}:</label>
        <textarea name="description" class="form-control i-description" id="i-p-description" minlength="20"
                  required></textarea>
        <input type="hidden" name="sectionId" value="${param.sectionId}" required>
        <button type="button" class="btn btn-cancel left" onclick="goToPreviousPage()">${locCancel}</button>
        <input type="submit" class="btn right" value="${locSave}"/>
    </form>
</section>
<%@include file="fragment/footer.jspf" %>
</fmt:bundle>