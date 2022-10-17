<%@ page import="com.ideas2it.training.model.Employee" %><%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 10/17/2022
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <div id="get_employee">
    <form action="delete_employee" method="GET">
      <label for="employee_id">Employee Id</label>
      <input type="text" name="employee_id" id="employee_id">
      <input type="submit" value="Get Employee">
    </form>
  </div>
  <%Employee employee = (Employee) request.getAttribute("employee");%>
  <%if (employee!=null){%>

  <div>
    <form method="post" action="delete_employee">
      <input type="text" name="employee_id" value="<%=employee.getEmployeeId()%>"hidden>
      <p>${employee}</p>
      <input type="submit" value="Delete Employee" style="background:#ff4000; color: black">
    </form>
  </div>
  <%} else {%>
  <p>${message}</p>
  <%}%>


</body>
</html>
