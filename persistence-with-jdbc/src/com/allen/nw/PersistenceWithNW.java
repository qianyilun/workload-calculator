package com.allen.nw;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.allen.QueueDays;
import com.allen.template.PersistenceWithTemplate;
import com.sap.security.core.server.csi.IXSSEncoder;
import com.sap.security.core.server.csi.XSSEncoder;

/**
 * Servlet implementation class PersistencyWithNW
 */
public class PersistenceWithNW extends PersistenceWithTemplate {
	private static final long serialVersionUID = 1L;
	private static final String LINKNAME = "nw";
	private static final String COMPONENT = "NW";
	private static final int FIXEDVALUE = 9999;
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
            nwDAO = new NWDAO(ds);
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
            
            checkCounter(request);
            
            pw.println("</center>");            
            pw.println("<body>");
            pw.println("</html>");                         
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
        }
    }   
    
    private void checkCounter(HttpServletRequest request) throws SQLException {
		// TODO Auto-generated method stub
    	String url = request.getQueryString();
    	
    	if (url == null) {
    		return;
    	}
    	if (url.contains("operation") || url.contains("ignore") ||url.contains("undo") || url.contains("delete")) {
    		nwDAO.addTimes();
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
                    "    <td><center><a style=\"color:blue\" href=\"ms\" >MS       </a></center></td>" + 
                    "    <td><center><a style=\"color:blue\" href=\"sa\">SA        </a></center></td>" + 
                    "    <td><center><a style=\"color:blue\" href=\"sm\">SM        </a></center></td>" + 
                    "    <td><center><a style=\"color:blue\" href=\"fc\">FC/EA/IC/FIM</a></center></td>" + 
                    "  </tr>" + 
                    "  <tr>" + 
                    "    <td><center><a style=\"color:blue\" href=\"dsm\">DSM</a></center></td>" + 
                    "    <td><center><a style=\"color:blue\" href=\"pcm\">PCM</a></center></td>" + 
                    "    <td><center>--></center></td>" + 
                    "    <td><center><a style=\"color:blue\" href=\"lod\">LOD-ANA-PL</a></center></td>" + 
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
    	response.setIntHeader("Refresh", 120);
    	
    	// Append table that lists all persons
        List<NW> resultList = nwDAO.selectAllEntries();
        response.getWriter().println(
                "<p><center><table width=70% border=\"1\"><tr><th colspan=\"1\"></th>" + "<th colspan=\"3\">" + (resultList.isEmpty() ? "" : resultList.size() + " ")
                        + "Entries in the Database</th>"
                        + "<th colspan=\"3\">" + "Smart Sorted</th></tr>");
        if (resultList.isEmpty()) {
            response.getWriter().println("<tr><td colspan=\"4\">Database is empty</td></tr>");
        } else {
            response.getWriter().println("<tr><th>#</th><th>Name</th><th>Increase</th><th>Decrease</th><th>Amount</th><th>Total</th><th>AVG Q-DAY</th></tr>");
        }
        IXSSEncoder xssEncoder = XSSEncoder.getInstance();
        int index = 1;
        Collections.sort(resultList); 
        
        // Add reset button
        response.getWriter().println("<p><center><form action=\"" + LINKNAME + "?operation=reset\" method=\"post\">" + "<input type=\"submit\" onclick=\"return window.confirm('Are you sure to RESET all values?')\" value=\"RESET\" />" + "</form></center></p>");

        response.getWriter().println("<h2>NW / XLS</h2>");
        
        for (NW nw : resultList) {
        	// Get score
        	String score = "0";
//        	if (nw.getNw() != 0) {
        		DecimalFormat df = new DecimalFormat("#.###");
        		score = df.format(((double)nw.getSum()) / QueueDays.hash.get(nw.getName()));
//        	}
        	
        	String pop = nw.getName() + " has been +1, please go for assign.";
        	String link = "<td><center><form action=\"" + LINKNAME + "?Id="+ nw.getId() + "&operation=add\" method=\"post\">" + "<input type=\"submit\" onclick=\"return window.prompt('" + pop + " Copy to clipboard: Ctrl+C, Enter','" + nw.getiNumber() + "')\" value=\"Add\" />" + "</form></center></td>";
        	
        	if (nw.getSum() < FIXEDVALUE) {
        		response.getWriter().println("<tr><td height=\"30\"><center>" + (index++) + "</center></td>");
	        	if (index == 2) {
	        		response.getWriter().println("<td height=\"30\"><center><mark><b>" + xssEncoder.encodeHTML(nw.getName()+" ("+nw.getiNumber()+")") + "</b></mark></center></td>");
	        	} else {
	        		response.getWriter().println("<td height=\"30\"><center>" + xssEncoder.encodeHTML(nw.getName()+" ("+nw.getiNumber()+")") + "</center></td>");
	        	}
        		response.getWriter().println(link); 
	        	response.getWriter().println("<td><center><form action=\"" + LINKNAME + "?Id="+ nw.getId() + "&operation=decrease\" method=\"post\">" + "<input type=\"submit\" value=\"Delete\" />" + "</form></center></td>"); 
	        	response.getWriter().println("<td height=\"30\"><center>" + nw.getNw() + "</center></td>");
				response.getWriter().println("<td height=\"30\"><center>" + nw.getSum() + "</center></td>" + "<td height=\"30\"><center>" + score + "</center></td>");
	        	response.getWriter().println("<td><center><form action=\"" + LINKNAME + "?Id="+ nw.getId() + "&operation=ignore\" method=\"post\">" + "<input type=\"submit\" onclick=\"return window.confirm('This person will be unavailable and you can undo anytime!')\" value=\"unavailable\" />" + "</form></center></td>");
        	} else {
	        	response.getWriter().println("<tr><td height=\"30\"><center>" + (index++) + "</center></td>");
	        	response.getWriter().println("<td height=\"30\"><center>" + xssEncoder.encodeHTML(nw.getName() + ": UNAVAILABLE") + "</center></td>");
	        	response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>"); 
	        	response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>"); 
	        	response.getWriter().println("<td height=\"30\"><center>" + (nw.getNw()-FIXEDVALUE) + "</center></td>");
				response.getWriter().println("<td height=\"30\"><center>" + (nw.getSum()-FIXEDVALUE) + "</center></td>");
				response.getWriter().println("<td><center><form action=\"" + LINKNAME + "?Id="+ nw.getId() + "&operation=undo\" method=\"post\">" + "<input type=\"submit\" value=\"undo\" />" + "</form></center></td>");
        	}
        	
			response.getWriter().println("</tr>");
        }
         
        response.getWriter().println("</table></center></p>");
        
        
        response.getWriter().println("<center><p>We have been serving " + nwDAO.getTimes() + " times</p></center>");
        
    }
    
    @Override
    protected void doIncrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // Extract name of person to be added from request
        String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = nwDAO.getAmount(COMPONENT, ID) + 1;
        	nwDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
    }

	@Override
	protected void doDecrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = nwDAO.getAmount(COMPONENT, ID) - 1;
        	nwDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
	}

	@Override
	protected void doReset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		nwDAO.resetIncidentToAll(COMPONENT);
		
		response.sendRedirect(LINKNAME);
	}

	@Override
	protected void doUndo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = nwDAO.getAmount(COMPONENT, ID) - FIXEDVALUE;
        	nwDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
	}

	@Override
	protected void doIgnore(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = nwDAO.getAmount(COMPONENT, ID) + FIXEDVALUE;
        	nwDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
	}

	@Override
	protected String getComponent() {
		// TODO Auto-generated method stub
		return null;
	}

}
