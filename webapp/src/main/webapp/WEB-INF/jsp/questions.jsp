<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="i18n">

<%@include file="fragment/header.jspf" %>
<fmt:message var="locQuestions" key="questions"/>
<fmt:message var="locNoQuestions" key="questions.noquestions"/>
<fmt:message var="locQuestion" key="question"/>
<fmt:message var="locConference" key="conference"/>
<fmt:message var="locFrom" key="from"/>
    <fmt:message var="locQuestions" key="questions"/>

<section>
    <h2>${locQuestions}</h2>
    <c:choose>
        <c:when test="${empty questions}">
            ${locNoQuestions}
        </c:when>
        <c:otherwise>
            <table id="questions">
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
                        <td><a href="${showQuestion}">${question.conferenceName}</a></td>
                        <c:if test="${userPrincipal.admin}">
                            <td><a href="${showQuestion}">${question.username}</a></td>
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