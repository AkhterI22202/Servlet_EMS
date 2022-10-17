<%--
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
<form action="employee" method="post">
    <label>Employee Type</label>
    <select name="employee_type" >
        <option value="Trainer">Trainer</option>
        <option value="Trainee">Trainee</option>
    </select>
    <br><br>
    <input type="text" name="employee_name" placeholder="Employee name" pattern="[A-Z][a-z]*([ ]{0,1}[a-z])*" required>
    <br><br>
    <select name="gender" >
        <option value="Male">Male</option>
        <option value="Female">Female</option>
        <option value="Other">Other</option>
    </select>
    <br><br>
    <input type="date" name="birthdate" placeholder="DOB" required><br><br>
    <input type="text" name="designation" placeholder="Designation" required><br><br>
    <input type="number" name="contact" placeholder="Contact" pattern="[6-9]{2}[0-9]{8}" required><br><br>
    <input type="email" name="email" placeholder="Email" required><br><br>
    <input type="text" name="probation" placeholder="Probation" required>
    <br><br>
    <input type="submit" >
</form>
</body>
</html>
