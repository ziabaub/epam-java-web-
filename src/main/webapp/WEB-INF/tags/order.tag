<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static.contents/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,300i,700&amp;subset=cyrillic"
      rel="stylesheet">

<fmt:bundle basename="page_content">
    <fmt:message key="client.destination" var="destination"/>
    <fmt:message key="client.lets_go" var="letsGo"/>
    <fmt:message key="client.reach" var="reach"/>
    <fmt:message key="client.telephone" var="phone"/>
    <fmt:message key="client.order_button" var="send"/>
    <fmt:message key="client.message" var="message"/>
    <fmt:message key="user.name" var="name"/>
    <fmt:message key="register.email" var="email"/>
</fmt:bundle>

<div class="wrapper_form_order" >
    <div class="content">
        <div class="txt1 animated" data-animation="fadeIn" data-animation-delay="100">${pageScope.letsGo}
        </div>
        <div class="txt2 animated" data-animation="fadeIn" data-animation-delay="150">${reach}
        </div>
    </div>
    <div>
        <form name="orderForm" method="POST" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="client_order"/>
            <p><input id = "first_name" class="log_input" type="text" name="name" placeholder="${pageScope.name}" value="" onkeyup="checkName()"/></p>
            <p><input id ="phone" class="log_input" type="text" name="phone" placeholder="${pageScope.phone}" value="" onkeyup="checkPhone()"/></p>
            <p><input id ="email" class="log_input" type="text" name="email" placeholder="${pageScope.email}" value="" onkeyup="checkEmail()"/></p>
            <p><input id = "zone" class="log_input" type="text" name="destination" placeholder="${pageScope.destination}" value="" onkeyup="checkZOne()"/></p>
            <p><textarea class="log_input" name="note" placeholder="${pageScope.message}" style="resize: none" rows="3"></textarea></p>
            <input  id="submit" class="log_button" type="submit" value="${pageScope.send}" disabled/>
        </form>
    </div>
</div>
<script>
<jsp:directive.include file="/static.contents/js/orderValidator.js"/>
</script>


