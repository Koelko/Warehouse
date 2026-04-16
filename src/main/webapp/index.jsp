<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Складская система</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <!-- Заголовок -->
    <header style="text-align: center; margin-bottom: 30px;">
        <h1>Складская система</h1>
        <p style="color: #666;">Управление товарами, поставками и продажами</p>
    </header>

    <h2 style="text-align: center; color: #333;">Справочники</h2>
    <ul class="menu-list">
        <li><a href="${pageContext.request.contextPath}/products">Товары</a></li>
        <li><a href="${pageContext.request.contextPath}/add-product">Добавить товар</a></li>

        <li><a href="${pageContext.request.contextPath}/suppliers">Поставщики</a></li>
        <li><a href="${pageContext.request.contextPath}/add-supplier">Добавить поставщика</a></li>

        <li><a href="${pageContext.request.contextPath}/customers">Покупатели</a></li>
        <li><a href="${pageContext.request.contextPath}/add-customer">Добавить покупателя</a></li>

        <li><a href="${pageContext.request.contextPath}/warehouses">Склады</a></li>
        <li><a href="${pageContext.request.contextPath}/add-warehouse">Добавить склад</a></li>
    </ul>

    <hr style="border: 0; border-top: 1px solid #eee; margin: 30px 0;">

    <h2 style="text-align: center; color: #333;">Операции</h2>
    <ul class="menu-list">
        <li><a href="${pageContext.request.contextPath}/stock">Остатки</a></li>
        <li><a href="${pageContext.request.contextPath}/sales">История продаж</a></li>
        <li><a href="${pageContext.request.contextPath}/add-stock">Приёмка</a></li>
        <li><a href="${pageContext.request.contextPath}/sell-goods">Продажа</a></li>
    </ul>
    <hr style="border: 0; border-top: 1px solid #eee; margin: 30px 0;">
    <div style="text-align: center; font-size: 0.9em; color: #888;">

        <p>Эксперименты:</p>
        <a href="${pageContext.request.contextPath}/demo/chain-validation" style="color: #999; text-decoration: underline;">
            Демо: Цепочка валидации
        </a>
    </div>
</div>
</body>
</html>