<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Редактировать товар</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>Редактировать товар</h1>

    <c:if test="${not empty error}">
        <div class="error"><c:out value="${error}"/></div>
    </c:if>

    <form action="${pageContext.request.contextPath}/update-product" method="post">
        <input type="hidden" name="id" value="${product.id}">

        <input type="text" name="name"
               value="<c:out value='${not empty product ? product.name : name}'/>"
               required>

        <input type="text" name="category"
               value="<c:out value='${not empty product ? product.category : category}'/>"
               required>

        <input type="number" step="0.01" name="price"
               value="${not empty product ? product.price : price}"
               required>

        <input type="text" name="manufacturer"
               value="<c:out value='${not empty product ? product.manufacturer : manufacturer}'/>"
               required>

        <button type="submit">Сохранить</button>
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/products">Назад к списку</a>
</div>
</body>
</html>