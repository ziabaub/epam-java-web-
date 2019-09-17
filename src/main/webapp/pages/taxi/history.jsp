<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="client.client_id" var="id"/>
    <fmt:message key="client.date" var="date"/>
    <fmt:message key="taxi.location" var="location"/>
    <fmt:message key="client.destination" var="destination"/>
    <fmt:message key="client.cost" var="cost"/>
    <fmt:message key="taxi.curr_order" var="curr_order"/>
</fmt:bundle>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/font-awesome.min.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<div class="table_user">
    <table>
        <tr>
            <th><span>&#8470;</span></th>
            <th>${pageScope.date}</th>
            <td>${pageScope.location}</td>
            <td>${pageScope.destination}</td>
            <td>${pageScope.cost}</td>
        </tr>
        <c:forEach var="taxi" items="${sessionScope.list}">
            <tr>
                <td>${taxi.id}</td>
                <td>${taxi.date}</td>
                <td>${taxi.clientZone}</td>
                <td>${taxi.destinationZone}</td>
                <td>${taxi.cost}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
