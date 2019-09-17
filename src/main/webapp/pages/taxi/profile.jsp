<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="taxi.profile_title" var="title"/>
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
<div class="table_user" >
    <table>
        <tr>
            <th>ID</th>
            <th>${sessionScope.user.id}</th>
        </tr>
        <tr>
            <th> ${pageScope.firstname} </th>
            <th>${sessionScope.user.firstName}</th>
        </tr>
        <tr>
            <th> ${pageScope.lastname} </th>
            <th>${sessionScope.user.lastName}</th>
        </tr>
        <tr>
            <th> ${pageScope.login} </th>
            <th>${sessionScope.user.login}</th>
        </tr>
        <tr>
            <th> ${pageScope.email} </th>
            <th>${sessionScope.user.email}</th>
        </tr>
    </table>
</div>
</body>
</html>
