<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,300i,700&amp;subset=cyrillic"
      rel="stylesheet">

<fmt:bundle basename="page_content">
    <fmt:message key="taxi.online" var="online"/>
    <fmt:message key="taxi.status" var="status"/>
</fmt:bundle>

<span>${pageScope.status}</span>
<span>${pageScope.online}</span>