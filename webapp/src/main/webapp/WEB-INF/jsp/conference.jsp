<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

<%@include file="fragment/header.jspf" %>
<fmt:message var="locWhere" key="conference.where"/>
<fmt:message var="locWhen" key="conference.when"/>
<fmt:message var="locSections" key="conference.sections"/>
<fmt:message var="locEdit" key="edit"/>
<fmt:message var="locDelete" key="delete"/>
<fmt:message var="locCreateProposal" key="conference.createproposal"/>
<fmt:message var="locAddSection" key="conference.addsection"/>
<fmt:message var="locAskQuestion" key="conference.askquestion"/>

<section>
    <h2 class="page-header">${conference.name}</h2>
    <p>${conference.description}</p>
    <p>${locWhere}: ${conference.address}</p>
    <p>${locWhen}: <fmt:formatDate value="${conference.date}"/></p>
    <table class="center">
        <tr>
            <th colspan="2">${locSections}</th>
        </tr>
        <tbody>
        <c:forEach items="${sections}" var="section">
            <tr>
                <td class="conference-description" title="view proposals">
                    <c:url var="showProposals" value="/proposal">
                        <c:param name="action" value="showAll"/>
                        <c:param name="sectionId" value="${section.id}"/>
                    </c:url>
                    <a href="${showProposals}">${section.topic}</a>
                </td>
                <td class="cell-controls">
                    <c:choose>
                        <c:when test="${userPrincipal.admin}">
                            <c:url var="editSection" value="/section">
                                <c:param name="action" value="update"/>
                                <c:param name="id" value="${section.id}"/>
                            </c:url>
                            <a href="${editSection}" class="btn" title="${locEdit}">
                                <i class="fa fa-edit"></i>
                            </a>
                            <c:url var="deleteSection" value="/section">
                                <c:param name="action" value="delete"/>
                                <c:param name="id" value="${section.id}"/>
                            </c:url>
                            <button type="button" class="btn btn-cancel"
                                    onclick="confirmAndRedirect('${deleteSection}')" title="${locDelete}">
                                <i class="fa fa-trash"></i>
                            </button>
                        </c:when>
                        <c:otherwise>
                            <c:url var="createProposal" value="/proposal">
                                <c:param name="action" value="create"/>
                                <c:param name="sectionId" value="${section.id}"/>
                            </c:url>
                            <a href="${createProposal}" class="btn"
                               title="${locCreateProposal}"><i class="fa fa-plus"></i></a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="container-center">
    <c:if test="${userPrincipal.admin}">
            <c:url value="/section" var="createSection">
                <c:param name="action" value="create"/>
                <c:param name="conferenceId" value="${conference.id}"/>
            </c:url>
            <a class="btn" href="${createSection}">${locAddSection}</a>

    </c:if>
    <c:if test="${not userPrincipal.admin}">
        <c:url value="/question" var="askQuestion">
            <c:param name="action" value="create"/>
            <c:param name="conferenceId" value="${conference.id}"/>
        </c:url>
        <a class="btn" href="${askQuestion}">${locAskQuestion}</a>
    </c:if>
    </div>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>
