<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

<%@include file="fragment/header.jspf" %>

<fmt:message var="locEnterMessage" key="message.enter"/>
<fmt:message var="locSend" key="send"/>


<section>
    <h2>${question.title}</h2>
    <div class="clearfix">
        <c:forEach var="message" items="${messages}">
            <div class="message-box ${message.sendByAdmin ? "right" : "left"}">${message.text}
                <span class="message-info">
            <fmt:formatDate value="${message.createTime}"/>
                ${message.username}
            </span>
            </div>
        </c:forEach>
    </div>

    <c:url var="createMessage" value="/question">
        <c:param name="action" value="processMessage"/>
    </c:url>
    <form class="text-center" action="${createMessage}" method="post">
        <input type="hidden" name="questionId" value="${question.id}" required/>
        <label for="i-message">${locEnterMessage}</label>
        <textarea id="i-message" name="text" minlength="5" pattern="(\S+\s?)*" required></textarea>
        <input class="btn" type="submit" value="${locSend}"/>
    </form>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>
