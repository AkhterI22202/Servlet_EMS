package org.example.demo7;

import com.ideas2it.MainController;
import com.ideas2it.training.model.Employee;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "updateEmployee", value = "/update_employee")
public class UpdateEmployee extends HttpServlet {
    private final MainController cont = new MainController();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException {
        PrintWriter out = response.getWriter();
        String employeeId = request.getParameter("employee_id");
        Employee employee = cont.getEmployee(employeeId);
        if(employee!=null){
            request.setAttribute("employee",employee);
            getServletContext().getRequestDispatcher("/update_employee.jsp").forward(request, response);
            //equest.setAttribute("employee",employee);
            //getServletContext().getRequestDispatcher("update_employee").forward(request, response);

        } else {
            out.println("employee no found");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String employeeId = request.getParameter("employee_id");
        Employee employee = cont.getEmployee(employeeId);
        //Employee employee = (Employee) request.getAttribute("employee");
        employee.setEmployeeType(request.getParameter("employee_type"));
        employee.setName(request.getParameter("employee_name"));
        employee.setGender(request.getParameter("gender"));
        employee.setBirthdate(request.getParameter("birthdate"));
        employee.setContact(request.getParameter("contact"));
        employee.setDesignation(request.getParameter("designation"));
        //employee.setEmail(request.getParameter("email"));
        employee.setProbationTime(request.getParameter("probation"));
        boolean status = cont.updateEmployee(employee);

        //String result = cont.addEmployee(request);
        PrintWriter out = response.getWriter();
        out.println("<h1>"+status+"</h1>");


    }

}