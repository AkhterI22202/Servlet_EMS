<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 10/12/2022
  Time: 12:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <form action="get-employee" >
      <select name="get_method">
          <option value="employee">Employee</option>
          <option value="employee_leaves">Employee Leaves</option>
          <option value="employee_projects">Employee Projects</option>
      </select>
    <input type="text" name="employee_id">
    <input type="submit" value="Get Employee">
  </form>
</body>
</html>
