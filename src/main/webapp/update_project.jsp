<%@ page import="com.ideas2it.training.model.Employee" %>
<%@ page import="com.ideas2it.training.model.LeaveRecords" %>
<%@ page import="com.ideas2it.training.model.Project" %><%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 10/12/2022
  Time: 10:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>updating project details</title>
</head>

<body>
    <div>
        <form action="update_project" method="GET" id="getDetails">
            <label for="project_id">project id</label>
            <input type="number" name="project_id" id="project_id">
            <input type="submit" value="Get Project">
        </form>
        <%Project project = (Project) request.getAttribute("project");%>
        <%if(project!=null){ %>
        <script>
            let form1 = document.getElementById("getDetails");
            form1.style.visibility = 'hidden';
        </script>
        <form action="update_project" method="POST">
            <label for="id" hidden>Project id</label>
            <input type="text" name="project_id" value="<%=project.getProjectId()%>" id="id" hidden><br>
            <label for="projectName">Project name</label>
            <input type="text" name="project_name" value="<%=project.getName()%>" id="projectName">
            <label for="startDate">Start Date</label>
            <input type="text" name="start_date" value="<%=project.getStartDate()%>" id="startDate">
            <label for="manager" hidden>Manager Name</label>
            <input type="text" name="manager" value="<%=project.getProjectManager()%>" id="manager" hidden >
            <label for="client">Description</label>
            <input type="text" name="description" value="<%=project.getDescription()%> "id="description">
            <label for="client">Client Name</label>
            <input type="text" name="client" value="<%=project.getClientName()%>" id="client">
            <input type="submit" value="Update Project">
        </form>

    </div>
<%}%>
    <p>${message}</p>

</body>
</html>

<script>


</script>