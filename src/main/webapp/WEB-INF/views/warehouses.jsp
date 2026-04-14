<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Склады</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>Склады</h1>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <table border="1">
        <tr>
            <th>Id</th>
            <th>Склад</th>
            <th>Адрес</th>
            <th>Действие</th>
        </tr>
        <c:forEach var="warehouse" items="${warehouses}">
            <tr>
                <td>${warehouse.id}</td>
                <td><c:out value="${warehouse.name}"/></td>
                <td><c:out value="${warehouse.address}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/edit-warehouse?id=${warehouse.id}">
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