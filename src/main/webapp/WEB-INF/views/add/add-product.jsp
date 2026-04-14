<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Добавить товар</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>Добавить товар</h1>
    <form action="${pageContext.request.contextPath}/add-product" method="post">
        Название: <input type="text" name="name" required><br><br>
        Категория: <input type="text" name="category" required><br><br>
        Цена: <input type="number" step="0.01" name="price" required><br><br>
        Производитель: <input type="text" name="manufacturer" required><br><br>
        <button type="submit">Добавить</button>
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/index.jsp">На главную</a>
</div>
</body>
</html>