<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 04/12/2025
  Time: 11:10 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Currency Converter</title>
</head>
<body>
    <h1>Chuyển đổi USD sang VNĐ</h1>
    <form action="convert" method="post">
        <label for="exchangeRate">Tỷ giá (VNĐ/USD):</label>
        <input type="number" id="exchangeRate" name="rate" required>
        <label for="usdAmount">Số tiền (USD):</label>
        <input type="number" id="usdAmount" name="usd" required>
        <input type="submit" value="Chuyển đổi">
    </form>
</body>
</html>
