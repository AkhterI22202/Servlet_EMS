<%@ page import="com.ideas2it.training.model.Employee" %>
<%@ page import="com.ideas2it.training.model.LeaveRecords" %><%--
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
  <div id="get_leave">
    <form action="delete_leave" method="GET">
      <label for="leave_id">Employee Id</label>
      <input type="text" name="leave_id" id="leave_id">
      <input type="submit" value="Get Leave">
    </form>
  </div>
  <%LeaveRecords leave = (LeaveRecords) request.getAttribute("leave");%>
  <%if (leave!=null){%>

  <div>
    <form method="post" action="delete_leave">
      <input type="text" name="leave_id" value="<%=leave.getLeaveId()%>"hidden>
      <p>${leave} <br> for Employee Id ${leave.getEmployee().getEmployeeId()}</p>
      <input type="submit" value="Delete Employee" style="background:#ff4000; color: black">
    </form>
  </div>
  <%} else {%>
  <p>${message}</p>
  <%}%>


</body>
</html>
