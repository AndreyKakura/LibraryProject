<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Title</title>
</head>
<jsp:include page="header.jsp"/>
<body>
<p><fmt:message key="home.title"/></p>
<c:if test="${not empty message}">
    <fmt:message key="${message}"/>
</c:if>
</body>
<jsp:include page="footer.jsp"/>
</html>
