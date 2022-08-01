<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Error 404</title>
</head>
<body>
<main>
    <form method="get" action="${pageContext.request.contextPath}/index.jsp">
        <div id="error">
            <div>
                <div id="number">
                    <div>404</div>
                    <div>Not Found</div>
                </div>
                <div><fmt:message key="error.404.message"/></div>
                <input type="submit" value="<fmt:message key="error.back"/>"/>
            </div>
        </div>
    </form>
</main>
</body>
</html>
