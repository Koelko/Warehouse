<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Покупатели</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>Покупатели</h1>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <table border="1">
        <tr>
            <th>Id</th>
            <th>Покупатель</th>
            <th>Контактная информация</th>
            <th>Действие</th>
        </tr>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.id}</td>
                <td><c:out value="${customer.name}"/></td>
                <td><c:out value="${customer.contactInfo}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/edit-customer?id=${customer.id}">
                        Редактировать
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br>
    <a href="${pageContext.request.contextPath}/index.jsp">На главную</a>
</div>
</body>
</html>