<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 10/12/2022
  Time: 2:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <form action="leave" method="post">
    <label>Employee Id</label><br>
    <input type="text" name="employee_id" required><br><br>
    <label>From Date</label><br>
    <input type="date" name="from_date" required><br><br>
    <label>To Date</label><br>
    <input type="date" name="to_date" required><br><br>
    <label>Leave Type</label><br>
    <select name="leave_type" required>
      <option value="Casual">Casual</option>
      <option value="Sick">Sick</option>
      <option value="Medical">Medical</option>
    </select><br><br>
    <input type="submit"  id="submit">
  </form>
</body>
</html>

<style>
  #submit {
    background-color: aquamarine;
    color: black;
  }

</style>