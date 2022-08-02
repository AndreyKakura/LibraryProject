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
<ul>
    <li><label class="px-1 fw-bold"><fmt:message key="account.login"/>:</label>${user.login}</li>
    <li><label class="px-1 fw-bold"><fmt:message key="account.surname"/>:</label>${user.surname}</li>
    <li><label class="px-1 fw-bold"><fmt:message key="account.name"/>:</label>${user.name}</li>
    <li><label class="px-1 fw-bold"><fmt:message key="account.email"/>:</label>${user.email}</li>
    <li><label class="px-1 fw-bold"><fmt:message key="account.phone"/>:</label>+${user.phone}</li>
    <li>${user.userRole}</li>
    <li>${user.userStatus}</li>
</ul>
<form method="post" action="${pageContext.request.contextPath}/controller?command=change_user_status_and_role&id=${user.id}">
    <div class="m-1 btn-group" role="group" aria-label="Basic radio toggle button group">
        <input type="radio" class="btn-check" name="role" id="admin_role" value="admin"
               autocomplete="off">
        <label class="btn btn-outline-primary" for="admin_role">
            <fmt:message key="account.role.admin"/></label>

        <input type="radio" class="btn-check" name="role" id="librarian_role"
               value="librarian" autocomplete="off">
        <label class="btn btn-outline-primary" for="librarian_role">
            <fmt:message key="account.role.librarian"/></label>

        <input type="radio" class="btn-check" name="role" id="user_role" value="user" checked
               autocomplete="off">
        <label class="btn btn-outline-primary" for="user_role">
            <fmt:message key="account.role.user"/></label>
    </div>
    <br/>
    <div class="m-1 btn-group" role="group" aria-label="Basic radio toggle button group">
        <input type="radio" class="btn-check" name="status" id="active_status" value="active" checked
               autocomplete="off">
        <label class="btn btn-outline-primary" for="active_status">
            <fmt:message key="account.status.active"/></label>

        <input type="radio" class="btn-check" name="status" id="blocked_status"
               value="blocked" autocomplete="off">
        <label class="btn btn-outline-primary" for="blocked_status">
            <fmt:message key="account.status.blocked"/></label>
    </div>
    <br/>
    <input type="submit" class="m-1 btn btn-outline-warning text-decoration-none text-dark"
           value="<fmt:message key="account.edit"/>"/>
</form>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>
