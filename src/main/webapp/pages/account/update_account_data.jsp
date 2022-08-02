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
    <form method="post" action="${pageContext.request.contextPath}/controller?command=update_account_data">
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
               placeholder="<fmt:message key="account.surname"/>"
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
        <label for="phone"><fmt:message key="account.phone"/></label><br/>
        <input type="tel" name="phone" id="phone" required
               placeholder="<fmt:message key="account.phone.placeholder"/>"
               pattern="\+375\d{9}"
               value="<c:out value="${requestScope.user.phone}"/>"
               title="<fmt:message key="account.phone.title"/>"
        />
        <br/>
        <label for="password"><fmt:message key="account.password"/></label><br/>
        <input type="password" name="password" id="password" required
               placeholder="<fmt:message key="account.password"/>"
               pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
               title="<fmt:message key="account.password.title"/>"
        />
        <br/><br/>
        <input type="submit" value="<fmt:message key="account.edit.submit"/>"/>
    </form>
    <br/>
    <hr/>
    <form method="post" action="${pageContext.request.contextPath}/controller?command=change_password">
        <label for="old_password"><fmt:message key="account.old_password"/></label><br/>
        <input type="password" name="old_password" id="old_password" required
               placeholder="<fmt:message key="account.old_password"/>"
               pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
               title="<fmt:message key="account.password.title"/>"
        />
        <br>
        <label for="new_password"><fmt:message key="account.new_password"/></label><br/>
        <input type="password" name="new_password" id="new_password" required
               placeholder="<fmt:message key="account.new_password"/>"
               pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
               title="<fmt:message key="account.password.title"/>"
        />
        <br/>
        <label for="repeated_password"><fmt:message key="account.repeated_password"/></label><br/>
        <input type="password" name="repeated_password" id="repeated_password" required
               placeholder="<fmt:message key="account.repeated_password"/>"
               pattern="[a-zA-Z][A-Za-z0-9]{7,29}"
               title="<fmt:message key="account.password.title"/>"
        />
        <input type="submit" value="<fmt:message key="account.edit.change_password"/>"/>
    </form>

    <c:if test="${not empty message}">
        <p class="alert-warning"><fmt:message key="${message}"/></p>
    </c:if>
</div>
</body>
<jsp:include page="../main/footer.jsp"/>

</html>
