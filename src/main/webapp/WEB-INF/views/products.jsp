<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Товары</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>Товары</h1>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <table border="1">
        <tr>
            <th>Id</th><th>Товар</th><th>Категория</th><th>Цена</th><th>Производитель</th><th>Действия</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td><c:out value="${product.name}"/></td>
                <td><c:out value="${product.category}"/></td>
                <td>${product.price}</td>
                <td><c:out value="${product.manufacturer}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/edit-product?id=${product.id}">
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