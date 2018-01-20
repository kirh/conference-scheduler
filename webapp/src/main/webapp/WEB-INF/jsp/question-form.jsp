<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

<%@include file="fragment/header.jspf" %>
<fmt:message var="locAskQuestion" key="conference.askquestion"/>
<fmt:message var="locTitle" key="title"/>
<fmt:message var="locQuestion" key="question"/>
<fmt:message var="locCancel" key="cancel"/>
<fmt:message var="locSend" key="send"/>

<section>
    <h2>${locAskQuestion}</h2>
    <c:url var="addQuestion" value="/question">
        <c:param name="action" value="process"/>
    </c:url>
    <form class="clearfix" action="${addQuestion}" method="post">
        <input type="hidden" name="conferenceId" value="${fn:escapeXml(param.conferenceId)}" required/>
        <label for="i-q-title">${locTitle}:</label>
        <input class="form-control" id="i-q-title" type="text" name="title" required/>
        <label for="i-q-text">${locQuestion}:</label>
        <input class="form-control" id="i-q-text" type="text" name="text" required/>
        <button type="button" class="btn btn-cancel left" onclick="goToPreviousPage()">${locCancel}</button>
        <input class="btn right" type="submit" value="${locSend}"/>
    </form>
</section>

<%@include file="fragment/footer.jspf"%>
</fmt:bundle>