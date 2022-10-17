package org.example.demo7;

import com.ideas2it.MainController;
import com.ideas2it.training.model.Employee;
import com.ideas2it.training.model.LeaveRecords;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "updateLeave", value = "/update_leave")
public class UpdateLeave extends HttpServlet {
    private final MainController cont = new MainController();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException {
        PrintWriter out = response.getWriter();
        int leaveId = Integer.parseInt(request.getParameter("leave_id"));
        LeaveRecords leave = cont.getLeaveByLeaveId(leaveId);
        if(leave!=null){
            request.setAttribute("leave",leave);
            getServletContext().getRequestDispatcher("/update_leave.jsp").forward(request, response);
        } else {
            request.setAttribute("message","leave not found");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/update_leave.jsp");
            requestDispatcher.include(request,response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException{
        //response.setContentType("text/html");
        int leaveId = Integer.parseInt(request.getParameter("leave_id"));
        //Employee employee = cont.getEmployee(employeeId);
        LeaveRecords leave = cont.getLeaveByLeaveId(leaveId);
        leave.setLeaveType(request.getParameter("leave_type"));

        boolean status = cont.updateLeaveRecord(leave);
        String message = "";
        if(status)
            message = "leave updated successfully";
        else
            message = "leave was not updated due to some reasons, try Again!";
        request.setAttribute("leave",leave);
        request.setAttribute("message",message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/update_leave.jsp");
        requestDispatcher.forward(request,response);
//        PrintWriter out = response.getWriter();
//        out.println("<h1>"+status+"</h1>");


    }

}