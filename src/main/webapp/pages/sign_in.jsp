<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Title</title>
</head>

<body>
<jsp:include page="main/header.jsp"/>
<main>
    <div class="container-fluid">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=sign_in">
            <%--    <input type="hidden" name="command" value="sign_in"/>--%>
            <label for="login"><fmt:message key="account.login"/> </label><br/>
            <input type="text" name="login" id="login" autocomplete="off" required
                   placeholder="<fmt:message key="account.login"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{3,19}"
            <%--            oninvalid="this.setCustomValidity('<fmt:message key="sign_in.login.title"/>')"--%>
                   value="<c:out value="${requestScope.user_login}"/>"
                   title="<fmt:message key="account.login.title"/>"
            />
            <br/>
            <label for="password"><fmt:message key="account.password"></fmt:message> </label><br/>
            <input type="password" name="password" id="password" required
                   placeholder="<fmt:message key="account.password"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                   value="<c:out value="${requestScope.user_password}"/>"
                   title="<fmt:message key="account.password.title"/>"
            />
            <br/>
            <br/>
            <input type="submit" value="<fmt:message key="sign_in.submit"/>"/>
        </form>
        <c:if test="${not empty message}">
            <fmt:message key="${message}"/>
        </c:if>
    </div>
</main>
</body>
<jsp:include page="main/footer.jsp"/>
</html>
