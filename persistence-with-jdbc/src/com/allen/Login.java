package com.allen;

import javax.servlet.http.*;
import java.io.*;

/*
** This class is for admin to Login
** @author Allen Qian
*/

public class Login extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            PrintWriter pw = res.getWriter();
            pw.println("<html>");
            pw.println("<body><center>");
            pw.println("<h1> SAP QueueManager Login </h1>");
            pw.println("<form action=check method=post>");

            pw.println("<p>Username</p>");
            pw.println("<input type=text name=username>");
            pw.println("<p>Password</p>");
            pw.println("<input type=password name=password>");                        
            pw.println("<br>");
            pw.println("<br>");
            pw.println("<input type=submit value=Submit>");
            
            pw.println("</form>");                        
            pw.println("</center></body>");            
            pw.println("</html>");

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        this.doGet(req, res);
    }
}