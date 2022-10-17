package org.example.demo7;

import com.google.gson.Gson;
import com.ideas2it.MainController;
import com.ideas2it.training.model.Employee;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "employee", value = "/employee")
public class EmployeeServlet extends HttpServlet {
    private final MainController cont = new MainController();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        MainController cont = new MainController();
        response.setContentType("Application/Json");
        PrintWriter out = response.getWriter();
        List<Employee> employees = cont.getEmployees();
        response.setStatus(200);
        for(Employee employee:employees){
            employee.setLeaves(null);
            employee.setProjects(null);
            String employeeStr = gson.toJson(employee);
            out.println(employeeStr);
            out.flush();
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String result = cont.addEmployee(request);
        PrintWriter out = response.getWriter();
        out.println("<h1>"+result+"</h1>");


    }

}