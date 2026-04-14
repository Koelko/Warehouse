<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Продажа товара</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h2>Продажа товара</h2>
    <form action="${pageContext.request.contextPath}/sell-goods" method="post">
        Товар:
        <select name="productId" required>
            <option value="">-- Выберите товар --</option>
            <c:forEach var="p" items="${products}">
                <option value="${p.id}">${p.name}</option>
            </c:forEach>
        </select><br><br>

        Покупатель:
        <select name="customerId" required>
            <option value="">-- Выберите покупателя --</option>
            <c:forEach var="c" items="${customers}">
                <option value="${c.id}">${c.name}</option>
            </c:forEach>
        </select><br><br>

        Склад:
        <select name="warehouseId" required>
            <option value="">-- Выберите склад --</option>
            <c:forEach var="w" items="${warehouses}">
                <option value="${w.id}">${w.name}</option>
            </c:forEach>
        </select><br><br>

        Количество: <input type="number" name="count" required><br><br>
        <button type="submit">Продать</button>
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/index.jsp">На главную</a>
</div>
</body>
</html>