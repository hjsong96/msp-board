<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html>
<head>
    <style>
        table, td, th {
            border : 1px solid black;
            border-collapse : collapse;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Spring Boot Application</title>
</head>
<body>
<h2>Sample User List</h2>
<table>
    <thead>
    <tr>
        <th>USER ID</th>
        <th>NAME</th>
        <th>USER_SN</th>
    </tr>
    </thead>
    <c:forEach var="item" items="${userList}" varStatus="idx">
        <tr>
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>${item.sn}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
