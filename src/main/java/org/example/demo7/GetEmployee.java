package org.example.demo7;

import com.google.gson.Gson;
import com.ideas2it.MainController;
import com.ideas2it.training.model.Employee;
import com.ideas2it.training.model.LeaveRecords;
import com.ideas2it.training.model.Project;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "getEmployee", value = "/get-employee")
public class GetEmployee extends HttpServlet {

    Gson gson = new Gson();
    MainController cont = new MainController();
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String employeeId = request.getParameter("employee_id");
        Employee employee = cont.getEmployee(employeeId);
        PrintWriter out = response.getWriter();

        response.setContentType("application/Json");
        String choice = request.getParameter("get_method");
        if (employee!=null) {
            if (choice.equals("employee")) {
                getEmployee(employee, response);

            } else if (choice.equals("employee_leaves")) {
                List<LeaveRecords> leaves = employee.getLeaves();
                getEmployee(employee, response);
                getEmployeeLeaves(leaves, response);
            } else if (choice.equals("employee_projects")) {
                List<Project> projects = employee.getProjects();
                getEmployee(employee, response);
                getEmployeeProjects(projects, response);
            }
        } else {
            response.setStatus(404);
            out.println(this.gson.toJson("employee not found"));
        }
    }

    void getEmployee (Employee employee, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        employee.setLeaves(null);
        employee.setProjects(null);
        String employeeStr = this.gson.toJson(employee);
        response.setStatus(200);
        out.println(employeeStr);
        out.println("Status : " + response.getStatus());
        out.println("content_type : " + response.getContentType());
        out.flush();
    }

    void getEmployeeLeaves(List<LeaveRecords> leaves , HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        if(leaves.size()>0) {

            for (LeaveRecords leave : leaves) {
                String jsonStr = this.gson.toJson(leave);
                out.println(jsonStr);
            }
            out.flush();
        } else {
            out.println("no leaves assosiated");
        }
    }

    void getEmployeeProjects(List<Project> projects,HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        if(projects.size()>0) {
            for (Project project : projects) {
                project.setEmployees(null);
                project.setEmployee(null);
                String jsonStr = this.gson.toJson(project);
                out.println(jsonStr);
            }
            out.flush();
        } else {
            out.println("No projects assigned");
        }
    }
}

