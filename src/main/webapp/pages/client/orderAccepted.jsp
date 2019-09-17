<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="page_content">
    <fmt:message key="client.order_accepted" var="accepted"/>
    <fmt:message key="client.cancel" var="cancel"/>
</fmt:bundle>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/main.css"/>
    <title>${pageScope.accepted}</title>
</head>
<header class="header">
    <h1 class="top">${pageScope.accepted}</h1>
</header>
<body class="page">
<div class="spinner">
    <div class ="cancel-button">
        <form name="loginForm" action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="cancel_order"/>
            <input class="log_button" type="submit" value="${pageScope.cancel}"/>
        </form>
    </div>
</div>

</body>
</html>
