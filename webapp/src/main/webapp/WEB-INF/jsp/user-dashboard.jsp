<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

<%@include file="fragment/header.jspf" %>

<fmt:message var="locDashboardHeader" key="participant.dashboard.header"/>
<fmt:message var="locNoProposals" key="proposals.noproposals"/>
<fmt:message var="locTitle" key="title"/>
<fmt:message var="locConference" key="conference"/>
<fmt:message var="locSection" key="section"/>
<fmt:message var="locStatus" key="proposal.status"/>
<fmt:message var="locDelete" key="delete"/>

<section>
    <h2>${locDashboardHeader}</h2>
    <c:choose>
        <c:when test="${empty proposals}">
            ${locNoProposals}
        </c:when>
        <c:otherwise>
            <table class="center">
                <thead>
                <tr>
                    <th>${locTitle}</th>
                    <th>${locConference}</th>
                    <th>${locSection}</th>
                    <th>${locStatus}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${proposals}" var="proposal">
                    <tr>
                        <td class="conference-name">${proposal.title}</td>
                        <td>${proposal.conferenceName}</td>
                        <td>${proposal.sectionName}</td>
                        <td>${proposal.status}</td>
                        <td>
                            <c:url var="deleteProposal" value="/proposal">
                                <c:param name="action" value="delete"/>
                                <c:param name="id" value="${proposal.id}"/>
                            </c:url>
                            <button type="button" class="btn btn-cancel"
                                    onclick="confirmAndRedirect('${deleteProposal}')"
                                    title="${locDelete}">
                                <i class="fa fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>