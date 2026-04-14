<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Продажи</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>История продаж</h1>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty sales}">
            <p>Продаж пока нет</p>
        </c:when>
        <c:otherwise>
            <table border="1">
                <tr>
                    <th>ID чека</th><th>Дата</th><th>Покупатель</th><th>Товары</th><th>Итого</th>
                </tr>
                <c:forEach var="sale" items="${sales}">
                    <tr>
                        <td>${sale.id}</td>
                        <td>${sale.date}</td>
                        <td>
                            <c:out value="${customerMap[sale.customerId].name}"/>
                        </td>
                        <td>
                            <c:forEach var="item" items="${sale.saleItems}" varStatus="status">
                                <c:out value="${productMap[item.productId].name}"/>
                                ${item.count} шт.<c:if test="${not status.last}">, </c:if>
                            </c:forEach>
                        </td>
                        <td>${sale.total}₽</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="${pageContext.request.contextPath}/index.jsp">На главную</a>
</div>
</body>
</html>