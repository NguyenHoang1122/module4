<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 08/12/2025
  Time: 4:28 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sandwich Order Success</title>
</head>
<body>
<h2>Gia vị lựa chọn cho vào bánh</h2>
<div>
    <c:if test="${empty condiments}">
        <p style="color: red">Bạn chưa chọn gia vị nào.</p>
    </c:if>
</div>
<ul>
    <c:forEach var="condiment" items="${condiments}">
        <li>${condiment}</li>
    </c:forEach>
</ul>
<p><a href="/">Quay lại trang chủ</a></p>
</body>
</html>
