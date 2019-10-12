<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<fmt:bundle basename="page_content">
    <fmt:message key="menu.title" var="title"/>
</fmt:bundle>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/main.css">
    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<c:if test="${(sessionScope.user.role == 'client')}">
    <tag:order/>
</c:if>

</body>
</html>
