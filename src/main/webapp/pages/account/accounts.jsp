<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Title</title>
</head>
<jsp:include page="../main/header.jsp"/>
<body>
<jsp:useBean id="user" class="com.kakura.libraryproject.entity.User" scope="page"/>

<ul class="list-group">
    <c:forEach var="user" items="${users}">
    <li class="list-group-item">
        <ul>
            <li><label class="px-1 fw-bold"><fmt:message key="account.login"/>:</label>${user.login}</li>
            <li><label class="px-1 fw-bold"><fmt:message key="account.surname"/>:</label>${user.surname}</li>
            <li><label class="px-1 fw-bold"><fmt:message key="account.name"/>:</label>${user.name}</li>
            <li><label class="px-1 fw-bold"><fmt:message key="account.email"/>:</label>${user.email}</li>
            <li><label class="px-1 fw-bold"><fmt:message key="account.phone"/>:</label>+${user.phone}</li>
            <li>${user.userRole}</li>
            <li>${user.userStatus}</li>
        </ul>
        <button class="m-1 btn btn-outline-warning">
            <a class="text-decoration-none text-dark"
               href="${pageContext.request.contextPath}/controller?command=go_to_account_management&id=${user.id}">
                <fmt:message key="account.edit"/>
            </a>
        </button>
            <%--        <form method="post" action="${pageContext.request.contextPath}/controller?command=change_user_status_and_role">--%>
            <%--            <div class="m-1 btn-group" role="group" aria-label="Basic radio toggle button group">--%>
            <%--                <input type="radio" class="btn-check" name="role${user.id}" id="admin_role${user.id}" value="admin"--%>
            <%--                       autocomplete="off">--%>
            <%--                <label class="btn btn-outline-primary" for="admin_role${user.id}">--%>
            <%--                    <fmt:message key="account.role.admin"/></label>--%>

            <%--                <input type="radio" class="btn-check" name="role${user.id}" id="librarian_role${user.id}"--%>
            <%--                       value="librarian" autocomplete="off">--%>
            <%--                <label class="btn btn-outline-primary" for="librarian_role${user.id}">--%>
            <%--                    <fmt:message key="account.role.librarian"/></label>--%>

            <%--                <input type="radio" class="btn-check" name="role${user.id}" id="user_role${user.id}" value="user"--%>
            <%--                       autocomplete="off">--%>
            <%--                <label class="btn btn-outline-primary" for="user_role${user.id}"><fmt:message--%>
            <%--                        key="account.role.user"/></label>--%>
            <%--            </div>--%>
            <%--            <br/>--%>
            <%--            <div class="m-1 btn-group" role="group" aria-label="Basic radio toggle button group">--%>
            <%--                <input type="radio" class="btn-check" name="status${user.id}" id="admin_status${user.id}" value="active"--%>
            <%--                       autocomplete="off">--%>
            <%--                <label class="btn btn-outline-primary" for="admin_status${user.id}">--%>
            <%--                    <fmt:message key="account.status.active"/></label>--%>

            <%--                <input type="radio" class="btn-check" name="status${user.id}" id="librarian_status${user.id}"--%>
            <%--                       value="blocked" autocomplete="off">--%>
            <%--                <label class="btn btn-outline-primary" for="librarian_status${user.id}">--%>
            <%--                    <fmt:message key="account.status.blocked"/></label>--%>
            <%--            </div>--%>
            <%--            <br/>--%>
            <%--            <input type="submit" class="m-1 btn btn-outline-warning text-decoration-none text-dark"--%>
            <%--                   value="<fmt:message key="account.edit"/>"/>--%>
            <%--        </form>--%>
        </c:forEach>
</ul>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>
