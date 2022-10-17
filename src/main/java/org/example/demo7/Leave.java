package org.example.demo7;

import com.google.gson.Gson;
import com.ideas2it.MainController;
import com.ideas2it.training.model.LeaveRecords;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "leave", value = "/leave")
public class Leave extends HttpServlet {
    private final MainController cont = new MainController();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        List<LeaveRecords> leaves = cont.getAllLeaves();
        for (LeaveRecords leave:leaves){
            leave.setEmployee(null);
            String jsonStr = gson.toJson(leave);
            out.println("<p>"+jsonStr+"</p>");
            //out.flush();
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message = cont.addLeaveRecords(request);
        PrintWriter out = response.getWriter();
        out.println("<h1>"+message+"</h1>");
    }

    public void destroy() {
    }
}