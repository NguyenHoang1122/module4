<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 04/12/2025
  Time: 11:31 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kết quả</title>
</head>
<body>
<h2>Kết quả</h2>
<c:if test="${not empty translation}">
    <p>Từ tiếng Anh: <strong>${word}</strong></p>
    <p>Nghĩa tiếng Việt: <strong>${translation}</strong></p>
</c:if>
<c:if test="${not empty message}">
    <p>Không tìm thấy từ: <strong>${word}</strong></p>
</c:if>

<a href="/">Quay lại trang từ điển</a>
</body>
</html>
