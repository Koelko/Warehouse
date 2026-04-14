<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ошибка</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h2>Ошибка</h2>
    <p class="error">${errorMessage}</p>
    <a href="${backLink}">Назад</a>
</div>
</body>
</html>