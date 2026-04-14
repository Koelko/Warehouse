<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Добавить поставщика</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>Добавить поставщика</h1>
    <form action="${pageContext.request.contextPath}/add-supplier" method="post">
        Название: <input type="text" name="name" required><br><br>
        Контактная информация: <input type="text" name="contactInfo" required><br><br>
        <button type="submit">Добавить</button>
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/index.jsp">На главную</a>
</div>
</body>
</html>