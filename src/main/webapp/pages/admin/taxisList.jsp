<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="menu.show_taxis" var="title"/>
    <fmt:message key="register.first_name" var="firstname"/>
    <fmt:message key="register.last_name" var="lastname"/>
    <fmt:message key="register.login" var="login"/>
    <fmt:message key="register.email" var="email"/>
    <fmt:message key="taxi.location" var="location"/>
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

<div class="table_taxi_admin">
    <p class="error">${requestScope.message}</p>
    <table>
        <tr>
            <th><span>&#8470;</span></th>
            <td>${pageScope.firstname} </td>
            <td>${pageScope.lastname}</td>
            <td>${pageScope.login} </td>
            <td>${pageScope.email} </td>
        </tr>
        <c:forEach var="taxi" items="${sessionScope.list}">
            <c:set var="count" value="${pageScope.count+1}"/>
            <tr>
                <td>${count}</td>
                <td>${taxi.firstName}</td>
                <td>${taxi.lastName}</td>
                <td>${taxi.login}</td>
                <td>${taxi.email}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="order_id" value=${taxi.id}>
                        <input type="hidden" name="command" value="delete_taxi"/>
                        <button onclick="confirm('sure you want to delete ${taxi.firstName}')" class="log_accept" type="submit" value="delete">delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
