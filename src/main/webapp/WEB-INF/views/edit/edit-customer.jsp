<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Редактирование покупателя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>Редактирование покупателя</h1>

    <c:if test="${not empty error}">
        <div class="error"><c:out value="${error}"/></div>
    </c:if>

    <form action="${pageContext.request.contextPath}/update-customer" method="post">
        <input type="hidden" name="id" value="${customer.id}">

        Имя:
        <input type="text" name="name" value="<c:out value='${customer.name}'/>" required><br><br>

        Контактная информация:
        <input type="text" name="contactInfo" value="<c:out value='${customer.contactInfo}'/>"><br><br>

        <button type="submit">Сохранить</button>
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/customers">Назад к списку</a>
</div>
</body>
</html>