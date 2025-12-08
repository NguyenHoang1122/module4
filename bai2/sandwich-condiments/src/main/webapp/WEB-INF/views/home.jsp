<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 08/12/2025
  Time: 4:27 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lựa chọn gia vị cho bánh</title>
    <style>
        .checkbox-group {
            display: flex;
            gap: 20px; /* Khoảng cách giữa các checkbox */
            align-items: center;
        }

        .divider {
            margin: 15px 0;
            border-top: 2px solid #ccc; /* Đường kẻ */
        }
    </style>
</head>
<body>
<h2>Lựa chọn gia vị cho bánh Sandwich</h2>
<form action="validate" method="post">
    <div class="checkbox-group">
        <label><input type="checkbox" name="condiments" value="Lettuce"> Lettuce</label>
        <label><input type="checkbox" name="condiments" value="Tomato"> Tomato</label>
        <label><input type="checkbox" name="condiments" value="Mustard"> Mustard</label>
        <label><input type="checkbox" name="condiments" value="Sprouts"> Sprouts</label>
    </div>

    <div class="divider"></div>

    <input type="submit" value="Submit">
</form>
</body>
</html>
