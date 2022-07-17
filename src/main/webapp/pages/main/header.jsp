<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Title</title>
</head>
<header>
    <fmt:message key="title"/>
    <div>
        <p><a href="${pageContext.request.contextPath}/controller?command=go_to_sign_up">
            <fmt:message key="header.sign_in"/> </a></p>
    </div>

    <br/>
    <hr/>
</header>
</html>
