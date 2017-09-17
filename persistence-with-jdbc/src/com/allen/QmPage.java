package com.allen;

import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


/*
** Show the Queue Manager webpage
** @author Allen Qian
*/

public class QmPage extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            PrintWriter pw = res.getWriter();

            // suppose a NORMAL user
            pw.println("<html>");
            pw.println("<head><title> Welcome back! </title></head>");
            pw.println("<body>");
            pw.println("<center>");
            pw.println("<h1> Welcome back, Queue Manager </h1>");

            drawUpperTable(pw);
            drawLowerTable(pw);

            pw.println("</center>");            
            pw.println("<body>");
            pw.println("</html>");        
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        doGet(req, res);
    }


    // table contains other Components except "NW"
    private void drawUpperTable(PrintWriter pw) {
        try {
            String upperTable = "" + 
            		"<style>" + 
            		"table {" + 
            		"    font-family: arial, sans-serif;" + 
            		"    border-collapse: collapse;" + 
            		"    width: 95%;" + 
            		"}" + 
            		"" + 
            		"td, th {" + 
            		"    border: 1px solid #dddddd;" + 
            		"    text-align: left;" + 
            		"    padding: 8px;" + 
            		"}" + 
            		"" + 
            		"</style>" + 
            		" " + 
            		"<h2>Other Components</h2>" + 
            		"<table>" + 
            		"  <tr>" + 
            		"    <td><center><a style=\"color:blue\" href=\"persistencewithms\" >MS		  </a></center></td>" + 
            		"    <td><center><a style=\"color:blue\" href=\"persistencewithsa\">SA		  </a></center></td>" + 
            		"    <td><center><a style=\"color:blue\" href=\"persistencewithsm\">SM		  </a></center></td>" + 
            		"    <td><center><a style=\"color:blue\" href=\"persistencewithfc\">FC/EA/IC/FIM</a></center></td>" + 
            		"  </tr>" + 
            		"  <tr>" + 
            		"    <td><center><a style=\"color:blue\" href=\"persistencewithdsm\">DSM</a></center></td>" + 
            		"    <td><center><a style=\"color:blue\" href=\"persistencewithpcm\">PCM</a></center></td>" + 
            		"    <td><center>--></center></td>" + 
            		"    <td><center><a style=\"color:blue\" href=\"persistencewithlod\">LOD-ANA-PL</a></center></td>" + 
            		"  </tr>" + 
            		"</table>" + 
            		"";
            
            pw.println(upperTable);       
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    // table contains "NW" only    
    private void drawLowerTable(PrintWriter pw) {
        try {
            String path = "C:\\Users\\I860745\\Documents\\Projects\\QueueManager_Web\\persistence-with-jdbc\\WebContent\\WEB-INF\\resourcelowerTable.html";
            String html = loadhtml(path);
            pw.println(html);       
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    private static String loadhtml(String path)
        throws IOException  {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }
}