<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: Nguyen Hoang93
  Date: 04/12/2025
  Time: 4:43 CH
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Customers List</title>
    <style>
        table {
            border: 1px solid #000;
            border-collapse: collapse;
        }

        th, td {
            border: 1px dotted #555;
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
<h1>Customers List</h1>
<p>There are ${customers.size()} customer(s) in list.</p>
<table>
    <caption>Customers List</caption>
    <thead>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>
        <th>Address</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="customer" items="${customers}">
        <tr>
        <td><c:out value="${customer.id}"/></td>

        <td><a href="${pageContext.request.contextPath}/customers/${customer.id}">${customer.name}</a></td>

        <td><c:out value="${customer.email}"/></td>

        <td><c:out value="${customer.address}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
