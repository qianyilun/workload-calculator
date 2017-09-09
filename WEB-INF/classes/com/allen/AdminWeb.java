package com.allen

import javax.servlet.http.*;
import java.io.*;

/*
** Show the Queue Manager webpage
** @author Allen Qian
*/

public class AdminWeb extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            PrintWriter pw = res.getWriter();
            pw.println("Queue Manager, welcome back!");

            
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        doGet(req, res);
    }
}