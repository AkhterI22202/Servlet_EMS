package org.example.demo7;

import com.ideas2it.MainController;
import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Project;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "updateProject", value = "/update_project")
public class UpdateProject extends HttpServlet {
    private final MainController cont = new MainController();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException {
        PrintWriter out = response.getWriter();
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        Project project = cont.getProjectByProjectId(projectId);
        if(project!=null){
            request.setAttribute("project",project);
            getServletContext().getRequestDispatcher("/update_project.jsp").forward(request, response);
        } else {
            request.setAttribute("message","project not found");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/update_project.jsp");
            requestDispatcher.forward(request,response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException{
        PrintWriter out = response.getWriter();
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        Project project = cont.getProjectByProjectId(projectId);
        out.println(project);
        project.setName(request.getParameter("project_name"));
        project.setDescription(request.getParameter("description"));
        project.setClientName(request.getParameter("client"));
        boolean status = cont.updateProjectDetails(project);
        String message = "";
        if(status)
            message = "project updated successfully";
        else
            message = "project was not updated due to some reasons, try Again!";
        request.setAttribute("project",project);
        request.setAttribute("message",message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/update_project.jsp");
        requestDispatcher.forward(request,response);
//        PrintWriter out = response.getWriter();
//        out.println("<h1>"+status+"</h1>");


    }

}