package com.allen;

import javax.servlet.http.*;
import java.io.*;

/*
** This class is for checking username and password match or not
** Please note, it is using SQL + Session + Cookie 
** @author Allen Qian
*/

public class Check extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            // Use Cookie and Session to save username/password
            // ----- hardcode the username/password so far----
            if (username.equals("allen") && password.equals("allen")) {
                // res.sendRedirect("welcome");
                System.out.println("hello");
            } else {
                res.sendRedirect("login");
            }
            
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}