<%@tag pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/font-awesome.min.css">

<fmt:bundle basename="page_content">
    <fmt:message key="menu.title" var="title"/>
    <fmt:message key="menu.hello" var="hello"/>
    <fmt:message key="menu.hello_guest" var="hello_guest"/>
    <fmt:message key="menu.login" var="login"/>
    <fmt:message key="menu.register" var="register"/>
    <fmt:message key="menu.logout" var="logout"/>
    <fmt:message key="menu.go_online" var="go_online"/>
    <fmt:message key="menu.go_offline" var="go_offline"/>
    <fmt:message key="menu.current_order" var="order"/>
    <fmt:message key="menu.history" var="history"/>
    <fmt:message key="menu.profile" var="profile"/>
    <fmt:message key="menu.show_taxis" var="show_taxis"/>
    <fmt:message key="menu.add_taxi" var="add_taxi"/>
    <fmt:message key="menu.find_taxi" var="find_taxi"/>
    <fmt:message key="admin.edit_rate" var="rate"/>
    <fmt:message key="menu.hostory.rate" var="history_rate"/>

</fmt:bundle>

<header class="header">
    <h1 class="top">${pageScope.title}</h1>
    <div class="change_level">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=common_change_language&locale=ru">RU</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=common_change_language&locale=by">BY</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=common_change_language&locale=en">EN</a>
            </li>
        </ul>
    </div>
    <div class="hello_message">
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <span class="hello_text">${pageScope.hello_guest}</span>
                <a class="logout_a"
                   href="${pageContext.request.contextPath}/pages/common/login.jsp" id="login">${pageScope.login}</a>
                <a class="register_login_a"
                   href="${pageContext.request.contextPath}/pages/common/register.jsp"
                   id="register">${pageScope.register}</a>
            </c:when>
            <c:otherwise>
                <span class="hello_text">${pageScope.hello} ${sessionScope.user.firstName} ${sessionScope.user.lastName}</span>
                <a class="register_login_a"
                   href="${pageContext.request.contextPath}/controller?command=common_logout">${pageScope.logout}</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>

<div class="user_menu">
    <ul>
        <c:if test="${sessionScope.user != null}">
            <li>
                <a href="${pageContext.request.contextPath}/pages/common/main.jsp">
                    <i class="fa fa-home" aria-hidden="true"></i>
                </a>
            </li>
            <c:choose>
                <c:when test="${sessionScope.user.role == 'taxi' }">
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=dispatcher">${pageScope.order}</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=history">${pageScope.history}</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/pages/taxi/profile.jsp">${pageScope.profile}</a>
                    </li>
                    <li>
                        <tag:status/>
                    </li>
                </c:when>
                <c:when test="${sessionScope.user.role == 'admin'}">
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=show_taxis">${pageScope.show_taxis}</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/pages/admin/editRate.jsp">${pageScope.rate}</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=rate_history">${pageScope.history_rate}</a>
                    </li>
                    <li>
                        <form id="find" name="FindForm" method="POST"
                              action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="find_taxi"/>
                            <label>${pageScope.find_taxi} <input type="text" name="name" value=""/></label>
                            <button type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
                        </form>
                    </li>
                </c:when>

            </c:choose>
        </c:if>
    </ul>
</div>