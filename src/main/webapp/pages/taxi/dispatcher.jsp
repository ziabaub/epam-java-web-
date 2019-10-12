<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="client.client_id" var="id"/>
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
            <td>${pageScope.location}</td>
            <td>${pageScope.destination}</td>
            <td>${pageScope.cost}</td>
        </tr>
        <c:forEach var="taxi" items="${sessionScope.list}">
            <c:set var="count" value="${pageScope.count+1}"/>
            <tr>
                <td>${count}</td>
                <td>${taxi.currArea}</td>
                <td>${taxi.destArea}</td>
                <td>${taxi.cost} $</td>
                <td>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="order_destination" value=${taxi.destArea}>
                        <input type="hidden" name="order_cost" value=${taxi.cost}>
                        <input type="hidden" name="order_id" value=${taxi.id}>
                        <input type="hidden" name="command" value="accept"/>
                        <input class="log_accept" type="submit" value="accept">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${sessionScope.order != null}">
        <div class="header">
            <h1 class="top">${pageScope.curr_order}</h1>
        </div>
        <table>
            <tr>
                <th><span>&#8470;</span></th>
                <td>${pageScope.destination}</td>
                <td>${pageScope.cost}</td>
            </tr>
            <tr>
                <td>1</td>
                <td>${sessionScope.order.destArea}</td>
                <td>${sessionScope.order.cost} $</td>
                <td>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="command" value="reach_destination"/>
                        <input class="log_accept" type="submit" value="done">
                    </form>
                </td>
            </tr>
        </table>
    </c:if>
</div>
</body>
</html>
