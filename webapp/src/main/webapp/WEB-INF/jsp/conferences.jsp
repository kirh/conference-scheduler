<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

    <%@include file="fragment/header.jspf" %>
    <fmt:message var="locNoConferences" key="conferences.noconferences"/>
    <fmt:message var="locName" key="name"/>
    <fmt:message var="locDescription" key="description"/>
    <fmt:message var="locDate" key="date"/>
    <fmt:message var="locWhere" key="conference.where"/>
    <fmt:message var="locConferences" key="conferences"/>


    <section>
        <h2 class="page-header">${locConferences}</h2>
        <c:choose>
            <c:when test="${empty conferences}">
                ${locNoConferences}
            </c:when>
            <c:otherwise>
                <table id="conferences" class="center">
                    <tr>
                        <th>${locName}</th>
                        <th>${locWhere}</th>
                        <th>${locDate}</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${conferences}" var="conference">
                        <tr>
                            <c:url var="showConference" value="/conference">
                                <c:param name="action" value="show"/>
                                <c:param name="id" value="${conference.id}"/>
                            </c:url>
                            <td class="conference-name"><a href="${showConference}">${conference.name}</a></td>
                            <td><a href="${showConference}">${conference.address}</a></td>
                            <td><a href="${showConference}"><fmt:formatDate value="${conference.date}"/></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </section>

    <%@include file="fragment/footer.jspf" %>
</fmt:bundle>
