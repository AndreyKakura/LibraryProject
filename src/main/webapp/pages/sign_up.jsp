<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Sign up</title>
</head>
<body>
<jsp:include page="main/header.jsp"/>
<main>
    <div class="container-fluid">
        <form method="post" action="${pageContext.request.contextPath}/controller?command=sign_up">
            <label for="login"><fmt:message key="account.login"/></label><br/>
            <input type="text" name="login" id="login" required
                   placeholder="<fmt:message key="account.login"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{1,19}"
                   value="<c:out value="${requestScope.user.login}"/>"
                   title="<fmt:message key="account.login.title"/>"
            />
            <br/>
            <label for="surname"><fmt:message key="account.surname"/></label><br/>
            <input type="text" name="surname" id="surname" required
                   placeholder="<fmt:message key="account.surname"/>"
                   pattern="[А-ЯA-Z][а-яёa-z]{1,19}"
                   value="<c:out value="${requestScope.user.surname}"/>"
                   title="<fmt:message key="account.surname.title"/>"
            />
            <br/>
            <label for="name"><fmt:message key="account.name"/></label><br/>
            <input type="text" name="name" id="name" required
                   placeholder="<fmt:message key="account.name"/>"
                   pattern="[А-ЯA-Z][а-яёa-z]{1,19}"
                   value="<c:out value="${requestScope.user.name}"/>"
                   title="<fmt:message key="account.surname.title"/>"
            />
            <br/>
            <label for="email"><fmt:message key="account.email"/></label><br/>
            <input type="email" name="email" id="email" required
                   placeholder="<fmt:message key="account.email"/>"
                   pattern="(([A-Za-z\d._]+){5,25}@([A-Za-z]+){3,7}\.([a-z]+){2,3})"
                   value="<c:out value="${requestScope.user.email}"/>"
                   title="<fmt:message key="account.email.title"/>"
            />
            <br/>
            <label for="phone_number"><fmt:message key="account.number"/></label><br/>
            <input type="tel" name="phone_number" id="phone_number" required
                   placeholder="<fmt:message key="account.number.placeholder"/>"
                   pattern="\+375\d{9}"
                   value="<c:out value="${requestScope.user.phone_number}"/>"
                   title="<fmt:message key="account.number.title"/>"
            />
            <br/>
            <label for="password"><fmt:message key="account.password"/></label><br/>
            <input type="password" name="password" id="password" required
                   placeholder="<fmt:message key="account.password"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                   value="<c:out value="${requestScope.user.password}"/>"
                   title="<fmt:message key="account.password.title"/>"
            />
            <br/>
            <label for="repeated_password"><fmt:message key="account.repeated_password"/></label><br/>
            <input type="password" name="repeated_password" id="repeated_password" required
                   placeholder="<fmt:message key="account.repeated_password"/>"
                   pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
                   value="<c:out value="${requestScope.user.repeated_password}"/>"
                   title="<fmt:message key="account.password.title"/>"
            />
            <br/><br/>
            <input type="submit" value="<fmt:message key="sign_up.submit"/>"/>
        </form>
        <c:if test="${not empty message}">
            <p class="alert-warning"><fmt:message key="${message}"/></p>
        </c:if>
    </div>
</main>
</body>
<jsp:include page="main/footer.jsp"/>
</html>
