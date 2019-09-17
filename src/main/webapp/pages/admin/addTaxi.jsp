<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="admin.permission_code" var="code"/>
    <fmt:message key="admin.role" var="role"/>
    <fmt:message key="admin.add" var="add"/>
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
<p class="error">${requestScope.message}</p>
<div class="wrapper_form">
    <form method="POST" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="add_permission"/>
        <p><label>${pageScope.code}<input class="log_input" type="text" name="code" value=""/></label></p>
        <p><label>${pageScope.role}<input class="log_input" type="text" name="role" value=""/></label></p>
        <input class="log_button" type="submit" value="${pageScope.add}"/>
    </form>
</div>
</body>
</html>
