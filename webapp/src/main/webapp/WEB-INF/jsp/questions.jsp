<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

<%@include file="fragment/header.jspf" %>
<fmt:message var="locQuestions" key="questions"/>
<fmt:message var="locNoQuestions" key="questions.noquestions"/>
<fmt:message var="locQuestion" key="question"/>
<fmt:message var="locConference" key="conference"/>
<fmt:message var="locFrom" key="from"/>

<section>
    <h2>Questions</h2>
    <c:choose>
        <c:when test="${empty questions}">
            ${locNoQuestions}
        </c:when>
        <c:otherwise>
            <table class="center">
                <thead>
                <tr>
                    <th>${locQuestion}</th>
                    <th>${locConference}</th>
                    <c:if test="${userPrincipal.admin}">
                        <th>${locFrom}</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${questions}" var="question">
                    <c:url value="/question" var="showQuestion">
                        <c:param name="action" value="show"/>
                        <c:param name="id" value="${question.id}"/>
                    </c:url>
                    <tr>
                        <td class="conference-name"><a href="${showQuestion}">${question.title}</a></td>
                        <td>${question.conferenceName}</td>
                        <c:if test="${userPrincipal.admin}">
                            <td>${question.username}</td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </ul>
        </c:otherwise>
    </c:choose>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>