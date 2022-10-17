<%@ page import="com.ideas2it.training.model.Employee" %>
<%@ page import="com.ideas2it.training.model.LeaveRecords" %><%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 10/12/2022
  Time: 10:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>inserting employee details into database</title>
</head>

<body>
    <div>
        <form action="update_leave" method="GET" id="getDetails">
            <label for="leave_id">leave id</label>
            <input type="text" name="leave_id" id="leave_id">
            <input type="submit" value="Get Leave">
        </form>
        <%LeaveRecords leave = (LeaveRecords) request.getAttribute("leave");%>
        <%if(leave!=null){ %>
        <script>
            let form1 = document.getElementById("getDetails");
            form1.style.visibility = 'hidden';
        </script>
        <form action="update_leave" method="POST">
            <input type="text" name="leave_id" value="<%=leave.getLeaveId()%>"hidden><br>
            <select name="leave_type">
                <option value="Casual">Casual</option>
                <option value="Sick">Sick</option>
                <option value="Medical">Medical</option>
            </select><br>
            <input type="submit" >
        </form>
        <p>${message}</p>
    </div>
<%}%>

</body>
</html>

<script>


</script>