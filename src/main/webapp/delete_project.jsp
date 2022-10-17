<%@ page import="com.ideas2it.training.model.Project" %><%--
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
  <div id="get_project">
    <form action="delete_project" method="GET">
      <label for="project_id">Employee Id</label>
      <input type="number" name="project_id" id="project_id">
      <input type="submit" value="Get Project">
    </form>
  </div>
  <%Project project = (Project) request.getAttribute("project");%>
  <%if (project!=null){%>

  <div>
    <form method="post" action="delete_project">
      <input type="text" name="project_id" value="<%=project.getProjectId()%>" hidden>
      <p>${project} <br> for Employee Id ${project.getEmployee().getEmployeeId()}</p>
      <input type="submit" value="Delete Employee" style="background:#ff4000; color: black">
    </form>
  </div>
  <%} else {%>
  <p>${message}</p>
  <%}%>
</body>
</html>
