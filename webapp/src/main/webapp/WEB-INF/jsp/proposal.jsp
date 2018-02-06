<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="i18n">

<%@include file="fragment/header.jspf" %>
<fmt:message var="locStatus" key="proposal.status"/>
<fmt:message var="locApprove" key="proposal.approve"/>
<fmt:message var="locReject" key="proposal.reject"/>

<section>
    <h2 class="page-header">${proposal.title}</h2>
    <p>${proposal.description}</p>
    <p>${locStatus}: ${proposal.status}</p>
    <c:if test="${userPrincipal.admin}">
    <c:url var="updateStatus" value="/proposal">
        <c:param name="action" value="update"/>
        <c:param name="id" value="${proposal.id}"/>
    </c:url>
        <div class="text-center">
    <button class="btn" type="button" onclick="confirmAndRedirect('${updateStatus}&status=approved')">
            ${locApprove}<i class="fa fa-check-square-o" title="${locApprove}"></i>
    </button>
    <button class="btn btn-cancel" type="button" onclick="confirmAndRedirect('${updateStatus}&status=rejected')">
            ${locReject}<i class="fa fa-ban" title="${locReject}"></i>
    </button>
            </div>
    </c:if>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>





