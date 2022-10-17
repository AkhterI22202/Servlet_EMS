package org.example.demo7;

import com.ideas2it.MainController;
import com.ideas2it.training.model.Project;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "projects", value = "/projects")
public class Projects extends HttpServlet {
    private final MainController cont = new MainController();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        List<Project> projects = cont.getAllProjects();
        out.println(projects);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = cont.addProject(request);
        PrintWriter out = response.getWriter();
        out.println("<h1>"+message+"</h1>");
    }

    public void destroy() {
    }
}