<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 05/12/2025
  Time: 5:05 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Email Validator</title>
</head>
<body>
<h1>Email Validate</h1>
<h3>${message}</h3>
<form action="validate" method="post">
    Enter your email :
    <input type="text" name="email" placeholder="Nhập email của bạn"><br>
    <input type="submit" value="Validate">
</form>
</body>
</html>
