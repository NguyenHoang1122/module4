<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 04/12/2025
  Time: 4:53 CH
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Customer Detail</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        .customer-info-container {
            border: 1px solid #ccc;
            padding: 20px;
            width: 400px;
            margin: 20px 0;
            box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
        }

        .customer-info-container h2 {
            margin-top: 0;
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
            font-size: 1.2em;
            color: #333;
        }

        .customer-info-container p {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .customer-info-container p strong {
            width: 80px; /* Align labels */
            display: inline-block;
            margin-right: 10px;
            color: #555;
        }

        .customer-info-container input[type="text"] {
            flex-grow: 1;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1em;
        }

        .customer-info-container button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1em;
            margin-top: 15px;
        }

        .customer-info-container button:hover {
            background-color: #45a049;
        }

        a {
            color: #007bff;
            text-decoration: none;
            margin-top: 20px;
            display: inline-block;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/customers/update">
    <div class="customer-info-container">
        <h2>Customer Information</h2>
        <p>
            <strong>Id:</strong>
            <input type="hidden" name="id" value="<c:out value="${customer.id}"/>">
            <input type="text" value="<c:out value="${customer.id}"/>" readonly>
        </p>
        <p>
            <strong>Name:</strong>
            <input type="text" name="name" value="<c:out value="${customer.name}"/>">
        </p>
        <p>
            <strong>Email:</strong>
            <input type="text" name="email" value="<c:out value="${customer.email}"/>">
        </p>
        <p>
            <strong>Address:</strong>
            <input type="text" name="address" value="<c:out value="${customer.address}"/>">
        </p>
        <button>Update</button>
    </div>
</form>
<p>
    <a href="${pageContext.request.contextPath}/customers">Back to Customers List</a>
</p>
</body>
</html>
