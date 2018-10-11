<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<html>
<head>
    <style type="text/css">
        tr.Exceed { color: red }
        tr.notExceed { color: green }
    </style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <th>
        <td>Date Time</td>
        <td>Description</td>
        <td>Calories</td>
        <td>Exceed</td>
    </th>
    <c:forEach items="${mealList}" var = "meal">
     <tr class="${meal.exceed ? 'Exceed' : 'NotExceed'}">

         <td><javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" /></td>
         <td>${meal.description}</td>
         <td>${meal.calories}</td>
         <td>${meal.exceed}</td>
     </tr>
 </c:forEach>
</table>
</body>
</html>
