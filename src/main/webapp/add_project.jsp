<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 10/12/2022
  Time: 11:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <form action="projects" method="post">
    <label>Project Name</label><br>
    <input type="text" name="project_name" required><br><br>

    <label>Start Date</label><br>
    <input type="date" name="start_date" required><br><br>

    <label>Description</label><br>
    <input type="text" name="description" required><br><br>

    <label>Project Manager ID</label><br>
    <input type="text" name="manager_id" required><br><br>

    <label>Client</label><br>
    <input type="text" name="client" required><br><br>

    <input type="submit"><br><br>
  </form>
</body>
</html>
