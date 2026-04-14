<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Редактировать поставщика</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h1>Редактировать поставщика</h1>

    <c:if test="${not empty error}">
        <div class="error"><c:out value="${error}"/></div>
    </c:if>

    <form action="${pageContext.request.contextPath}/update-product" method="post">
        <input type="hidden" name="id" value="${supplier.id}">

        <input type="text" name="name"
               value="<c:out value='${not empty supplier ? supplier.name : name}'/>"
               required>

        <input type="text" name="contactInfo"
               value="<c:out value='${not empty supplier ? supplier.contactInfo : contactInfo}'/>"
               required>

        <button type="submit">Сохранить</button>
    </form>

    <br>
    <a href="${pageContext.request.contextPath}/suppliers">Назад к списку</a>
</div>
</body>
</html>