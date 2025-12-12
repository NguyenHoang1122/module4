<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 12/12/2025
  Time: 9:23 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calculator</title>
</head>
<body>

<h1>Caculator</h1>

<form action="/calculate" method="get">

    <input type="number" step="any" name="num1" value="${num1}" required>
    <input type="number" step="any" name="num2" value="${num2}" required>
    <br><br>

    <button type="submit" name="operator" value="add">Addition(+)</button>
    <button type="submit" name="operator" value="sub">Subtraction(-)</button>
    <button type="submit" name="operator" value="mul">Multiplication(x)</button>
    <button type="submit" name="operator" value="div">Division(/)</button>

</form>

<br>

<h3>Kết quả:</h3>
<p>${message}</p>
<p>${result}</p>

</body>
</html>

