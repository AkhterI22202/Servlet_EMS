<%@ page import="com.ideas2it.training.model.Employee" %><%--
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


    <form action="update_employee" method="GET" id="getDetails">
        <label for="employee_id">EmployeeId</label>
        <input type="text" name="employee_id" id="employee_id">
        <input type="submit" value="Get Employee">
    </form>

    <%Employee employee = (Employee) request.getAttribute("employee");%>
        <%if(employee!=null){ %>
            <script>
                    let form1 = document.getElementById("getDetails");
                    form1.style.visibility = 'hidden';
            </script>
    `<form action="update_employee" method="post">


        <input type="text" name="employee_id" value="<%=employee.getEmployeeId()%>"hidden>
        <label>Employee Type</label>
        <select name="employee_type" value="<%=employee.getEmployeeType()%>" >
            <option value="Trainer">Trainer</option>
            <option value="Trainee">Trainee</option>
        </select>
        <br><br>
        <input type="text" name="employee_name" placeholder="Employee name" pattern="[A-Z][a-z]*([ ]{0,1}[a-z])*" value="<%=employee.getName()%>"required>
        <br><br>
        <select name="gender" value="<%=employee.getGender()%>">
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Other">Other</option>
        </select>
        <br><br>
        <input type="date" name="birthdate" placeholder="DOB" value="<%=employee.getBirthdate()%>" required><br><br>
        <input type="text" name="designation" placeholder="Designation" value="<%=employee.getDesignation()%>" required><br><br>
        <input type="number" name="contact" placeholder="Contact" pattern="[6-9]{2}[0-9]{8}" value="<%=employee.getContact()%>" required><br><br>
        <input type="email" name="email" placeholder="Email" value=<%=employee.getEmail()%> readonly><br><br>
        <input type="text" name="probation" placeholder="Probation" value="<%=employee.getProbationTime()%>" required>
        <br><br>
        <input type="submit" >

    </form>
    <%}%>
</body>
</html>
