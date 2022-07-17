<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Title</title>
</head>
<jsp:include page="main/header.jsp"/>
<body>

<form method="post" action="${pageContext.request.contextPath}/controller?command=sign_in">
    <%--    <input type="hidden" name="command" value="sign_in"/>--%>
    <input type="text" name="login" placeholder="<fmt:message key="sign_in.login.placeholder"/>"
           pattern="[a-zA-Z][A-Za-z0-9]{3,29}"
    <%--           required oninvalid="this.setCustomValidity('<fmt:message key="sign_in.login.title"/>')"--%>
           autocomplete="off"
           value="<c:out value="${requestScope.user_login}"/>"
           title="<fmt:message key="sign_in.login.title"/>"
    />
    <br/>
    <br/>
    <input type="password" name="password"
           placeholder="<fmt:message key="sign_in.password.placeholder"/>"
           pattern="[a-zA-Z][A-Za-z0-9]{3,29}"
           value="<c:out value="${requestScope.user_password}"/>" required
           title="<fmt:message key="sign_in.password.title"/>"
    />
    <br/>
    <br/>
    <input type="submit" value="submit"/>
</form>
<c:if test="${not empty message}">
    <fmt:message key="${message}"/>
</c:if>
</body>
<jsp:include page="main/footer.jsp"/>
</html>
