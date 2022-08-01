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
<div class="container-fluid">
    <dl class="row">
        <dt class="col-sm-3"><fmt:message key="account.login"/></dt>
        <dd class="col-sm-9">${sessionScope.user.login}</dd>

        <dt class="col-sm-3"><fmt:message key="account.surname"/></dt>
        <dd class="col-sm-9">${sessionScope.user.surname}</dd>

        <dt class="col-sm-3"><fmt:message key="account.name"/></dt>
        <dd class="col-sm-9">${sessionScope.user.name}</dd>

        <dt class="col-sm-3"><fmt:message key="account.email"/></dt>
        <dd class="col-sm-9">${sessionScope.user.email}</dd>

        <dt class="col-sm-3"><fmt:message key="account.number"/></dt>
        <dd class="col-sm-9">+${sessionScope.user.phoneNumber}</dd>
    </dl>
        <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/controller?command=go_to_update_account_data">
            <fmt:message key="account.edit"/>
        </a>
</div>
</body>
<jsp:include page="../main/footer.jsp"/>
</html>
