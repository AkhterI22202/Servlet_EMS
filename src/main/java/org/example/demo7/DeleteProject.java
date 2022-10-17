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

@WebServlet(name = "DeleteProject", value = "/delete_project")
public class DeleteProject extends HttpServlet {

    private final MainController cont = new MainController();
    String message = "";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException {

        Project project= cont.getProjectByProjectId(Integer.parseInt(request.getParameter("project_id")));
        if (project!=null){
            request.setAttribute("project",project);
        } else {
            message = "project does not exist";
            request.setAttribute("message",message);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete_project.jsp");
        requestDispatcher.forward(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
        Project project = cont.getProjectByProjectId(Integer.parseInt(request.getParameter("project_id")));
        boolean status = cont.deleteProject(project);
        if (status)
            message = "project deleted successfully";
        else
            message = "couldn't delete the project record, try after some time!";
        request.setAttribute("message",message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete_project.jsp");
        requestDispatcher.forward(request,response);
    }
}