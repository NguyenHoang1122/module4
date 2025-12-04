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
    <title>Từ điển Anh Việt</title>
</head>
<body>
<h2>Từ điển Anh - Việt</h2>
<form action="/translate" method="post">
    Nhập từ tiếng Anh cần dịch: <input type="text" name="word" required/>
    <input type="submit" value="Dịch"/>
</form>
</body>
</html>
