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

@WebServlet(name = "DeleteLeave", value = "/delete_leave")
public class DeleteLeave extends HttpServlet {

    private final MainController cont = new MainController();
    String message = "";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException {

        LeaveRecords leave = cont.getLeaveByLeaveId(Integer.parseInt(request.getParameter("leave_id")));
        if (leave!=null){
            request.setAttribute("leave",leave);
        } else {
            message = "leave does not exist";
            request.setAttribute("message",message);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete_leave.jsp");
        requestDispatcher.forward(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
        LeaveRecords leave = cont.getLeaveByLeaveId(Integer.parseInt(request.getParameter("leave_id")));
        boolean status = cont.deleteLeave(leave);
        if (status)
            message = "leave deleted successfully";
        else
            message = "couldnt delete the Leave record, try after some time!";
        request.setAttribute("message",message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete_leave.jsp");
        requestDispatcher.forward(request,response);
    }
}