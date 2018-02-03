<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="i18n">

<%@include file="fragment/header.jspf" %>
<fmt:message var="locProposals" key="proposals"/>
<fmt:message var="locNoProposals" key="proposals.noproposals"/>
<fmt:message var="locBack" key="back"/>


<section>
    <h2 class="page-header">${param.sectionTopic}</h2>
    <table class="center">
        <tr>
            <th colspan="3">Proposals</th>
        </tr>
        <tbody>
        <c:if test="${empty proposals}">
            <tr><td class="text-center" colspan="2">No proposals</td></tr>
        </c:if>
        <c:forEach items="${proposals}" var="proposal">
            <tr>
                <td class="name">
                    <c:url value="/proposal" var="showProposal">
                        <c:param name="action" value="show"/>
                        <c:param name="id" value="${proposal.id}"/>
                    </c:url>
                    <a href="${showProposal}">${proposal.title}</a></td>
                <td><a href="${showProposal}">${proposal.username}</a></td>
                <td><a href="${showProposal}">${proposal.status}</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="text-center">
    <button type="button" class="btn btn-cancel" onclick="goToPreviousPage()">Back</button>
    </div>
</section>

<%@include file="fragment/footer.jspf" %>
</fmt:bundle>