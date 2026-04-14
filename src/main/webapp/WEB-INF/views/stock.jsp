<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Остатки по складам</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>Остатки по складам</h1>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <table border="1">
        <tr>
            <th>Id</th><th>Товар</th><th>Склад</th><th>Поставщик</th><th>Количество</th>
        </tr>
        <c:forEach var="item" items="${stock}">
        <tr>
            <td>${item.id}</td>
            <td>
                <c:out value="${not empty productMap[item.productId] ? productMap[item.productId].name : '-'}"/>
            </td>
            <td>
                <c:out value="${not empty warehouseMap[item.warehouseId] ? warehouseMap[item.warehouseId].name : '-'}"/>
            </td>
            <td>
                <c:out value="${not empty supplierMap[item.supplierId] ? supplierMap[item.supplierId].name : '-'}"/>
            </td>
            <td>${item.count}</td>
        </tr>
        </c:forEach>
    </table>

    <br>
    <a href="${pageContext.request.contextPath}/index.jsp">На главную</a>
</div>
</body>
</html>