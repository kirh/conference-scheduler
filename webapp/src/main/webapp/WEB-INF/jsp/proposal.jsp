<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="local">

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
    <button class="btn" type="button" onclick="confirmAndRedirect('${updateStatus}&status=approved')">
        <i class="fa fa-check-square-o" title="${locApprove}"></i>
    </button>
    <button class="btn btn-cancel" type="button" onclick="confirmAndRedirect('${updateStatus}&status=rejected')">
        <i class="fa fa-ban" title="${locReject}"></i>
    </button>
    </c:if>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>





