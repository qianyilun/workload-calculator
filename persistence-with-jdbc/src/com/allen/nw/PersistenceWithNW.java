package com.allen.nw;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.allen.template.PersistenceWithTemplate;
import com.sap.security.core.server.csi.IXSSEncoder;
import com.sap.security.core.server.csi.XSSEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class PersistencyWithNW
 */
public class PersistenceWithNW extends PersistenceWithTemplate {
	private static final long serialVersionUID = 1L;
	private static final String linkName = "persistencewithnw";
	private NWDAO nwDAO; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersistenceWithNW() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** {@inheritDoc} */
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
            nwDAO = new NWDAO(ds,"NW");
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
	    	PrintWriter pw = response.getWriter();
	
	        // suppose a NORMAL user
	        pw.println("<html>");
	        pw.println("<head><title> Welcome back! </title></head>");
	        pw.println("<body>");
	        pw.println("<center>");
	        pw.println("<h1> Welcome back, Queue Manager </h1>");
	
	        drawUpperTable(pw);
        	displayTable(response);
        	
        	pw.println("</center>");            
            pw.println("<body>");
            pw.println("</html>");                         
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
        }
    }   
    
    private void drawUpperTable(PrintWriter pw) {
		// TODO Auto-generated method stub

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

	@Override
    protected void displayTable(HttpServletResponse response) throws SQLException, IOException {
        // Append table that lists all persons
        List<NW> resultList = nwDAO.selectAllEntries();
        response.getWriter().println("<br><br><h2>NW Component</h2>");
        response.getWriter().println(
                "<p><center><table width=70% border=\"1\"><tr><th colspan=\"1\"></th>" + "<th colspan=\"3\">" + (resultList.isEmpty() ? "" : resultList.size() + " ")
                        + "Entries in the Database</th>"
                        + "<th colspan=\"3\">" + "Smart Sorted</th></tr>");
        if (resultList.isEmpty()) {
            response.getWriter().println("<tr><td colspan=\"4\">Database is empty</td></tr>");
        } else {
            response.getWriter().println("<tr><th>#</th><th>Name</th><th>Increase</th><th>Decrease</th><th>Amount</th><th>Total</th><th>Score</th></tr>");
        }
        IXSSEncoder xssEncoder = XSSEncoder.getInstance();
        int index = 1;
        Collections.sort(resultList);
        for (NW nw : resultList) {
        	response.getWriter().println(
                    "<tr><td height=\"30\"><center>" + (index++) + "</center></td>"
                    + "<td height=\"30\"><center>" + xssEncoder.encodeHTML(nw.getName()) + "</center></td>"
					+ "<td><center><form action=\"" + linkName + "?Id="+ nw.getId() + "\"method=\"post\">" + "<input type=\"submit\" value=\"Add\" />" + "</form></center></td>" 
					+ "<td>" + "<center><input type=\"submit\" value=\"-\"></center>" + "</td>"
					+ "<td height=\"30\"><center>" + nw.getAmount() + "</center></td>" // need to change to xssEncoder for getAmount()?
					+ "<td height=\"30\"><center>" + nw.getTotal() + "</center></td>" // need to change to xssEncoder for getTotal()?
					+ "<td height=\"30\"><center>" + String.format("%.3f", (nw.getAmount()*0.8 + (nw.getTotal()-nw.getAmount())/nw.getAmount()*0.2 + 10),3) + "</center></td>"
					+ "</tr>");
        }
        response.getWriter().println("</table></center></p>");
    }
    
    @Override
    protected void doIncrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // Extract name of person to be added from request
        String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int temp = Integer.parseInt(id);
        	int amount = nwDAO.getAmount(temp) + 1;
        	nwDAO.addIncidentToPerson(id, amount);
        }
    }

}
