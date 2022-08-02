<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=go_to_home">
                <fmt:message key="title"/></a>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="${pageContext.request.contextPath}/controller?command=go_to_home">
                            <fmt:message key="header.home"/> </a>
                    </li>
                    <c:choose>
                        <c:when test="${sessionScope.role eq 'admin'}">
                            <li class="nav-item px-1">
                                <a class="nav-link btn btn-outline-success"
                                   href="${pageContext.request.contextPath}/controller?command=go_to_accounts_management">
                                    <fmt:message key="header.users"/>
                                </a>
                            <li/>
                        </c:when>
                    </c:choose>
                    <c:choose>
                        <c:when test="${sessionScope.role eq 'guest'}">
                            <li class="nav-item px-1">
                                <a class="nav-link btn btn-outline-success"
                                   href="${pageContext.request.contextPath}/controller?command=go_to_sign_in">
                                    <fmt:message key="header.sign_in"/>
                                </a>
                            <li/>
                            <li class="nav-item px-1">
                                <a class="nav-link  btn btn-outline-info"
                                   href="${pageContext.request.contextPath}/controller?command=go_to_sign_up">
                                    <fmt:message key="header.sign_up"/>
                                </a>
                            <li/>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item px-1">
                                <a class="nav-link  btn btn-outline-success"
                                   href="${pageContext.request.contextPath}/controller?command=go_to_account">
                                    <fmt:message key="header.account"/>
                                </a>
                            <li/>
                            <li class="nav-item px-1">
                                <a class="nav-link  btn btn-outline-warning"
                                   href="${pageContext.request.contextPath}/controller?command=sign_out">
                                    <fmt:message key="header.sign_out"/>
                                </a>
                            <li/>
                        </c:otherwise>
                    </c:choose>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <fmt:message key="language"/>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=change_locale&language=RU">
                                <fmt:message key="language.ru"/></a></li>
                            <li><a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/controller?command=change_locale&language=EN">
                                <fmt:message key="language.en"/></a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <hr/>
</header>
<script src="${pageContext.request.contextPath}/bootstrap-5.1.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
