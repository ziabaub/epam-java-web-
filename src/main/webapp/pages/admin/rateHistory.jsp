<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="client.date" var="date"/>
    <fmt:message key="admin.rate" var="rate"/>
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
            <td>${pageScope.rate}</td>
        </tr>
        <c:forEach var="rate" items="${sessionScope.list}">
            <c:set var="count" value="${pageScope.count+1}"/>
            <tr>
                <td>${count}</td>
                <td>${rate.date}</td>
                <td>${rate.rate} $</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
