<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="i18n">

<%@include file="fragment/header.jspf" %>
<fmt:message var="locAdminDashboardHeader" key="admin.dashboard.header"/>
<fmt:message var="locEdit" key="edit"/>
<fmt:message var="locDelete" key="delete"/>
<fmt:message var="locAddConference" key="admin.dashboard.addconference"/>

<section>
    <h2 class="page-header">${locAdminDashboardHeader}</h2>
    <table class="center">
        <tbody>
        <c:forEach items="${conferences}" var="conference" varStatus="status">
            <c:set var="id" value="conference-${status.count}"/>
            <tr id="${id}">
                <c:url var="showConference" value="/conference?action=show&id=${conference.id}"/>
                    <td class="name"><a href="${showConference}">${conference.name}</a></td>
                <td>
                    <a href="${showConference}"> <fmt:formatDate value="${conference.date}"/></a>
                </td>

                <td class="cell-controls">
                    <c:url var="editConference" value="/conference">
                        <c:param name="action" value="update"/>
                        <c:param name="id" value="${conference.id}"/>
                    </c:url>
                    <a href="${editConference}" class="btn" title="${locEdit}">
                        <i class="fa fa-edit"></i>
                    </a>
                    <c:url var="deleteConference" value="/conference">
                        <c:param name="action" value="delete"/>
                        <c:param name="id" value="${conference.id}"/>
                    </c:url>
                    <button type="button" class="btn btn-cancel"
                            onclick="confirmAndRemove('${id}', '${deleteConference}')"
                            title="${locDelete}">
                        <i class="fa fa-trash"></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="text-center">
        <c:url value="/conference" var="conferenceLink">
            <c:param name="action" value="add"/>
        </c:url>
        <a class="btn btn-large" href="${conferenceLink}">${locAddConference}</a>
    </div>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>

