package org.example.demo7;

import com.ideas2it.MainController;
import com.ideas2it.training.model.Employee;
import com.ideas2it.training.model.Project;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeleteEmployee", value = "/delete_employee")
public class DeleteEmployee extends HttpServlet {

    private final MainController cont = new MainController();
    String message = "";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException {

        Employee employee = cont.getEmployee(request.getParameter("employee_id"));
        if (employee!=null){
            request.setAttribute("employee",employee);
        } else {
            message = "employee does not exist";
            request.setAttribute("message",message);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete_employee.jsp");
        requestDispatcher.forward(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
        //Employee employee = cont.getEmployee(request.getParameter("employee_id"));
        Employee employee = cont.getEmployee(request.getParameter("employee_id"));
        boolean status = cont.deleteEmployee(employee);
        if (status)
            message = "employee deleted successfully";
        else
            message = "couldnt delete the employee, try after some time!";
        request.setAttribute("message",message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete_employee.jsp");
        requestDispatcher.forward(request,response);
    }
}