package com.allen.sa;

import java.io.IOException;
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
 * Servlet implementation class PersistencyWithSA
 */
public class PersistenceWithSA extends PersistenceWithTemplate {
	private static final long serialVersionUID = 1L;
	private static final String LINKNAME = "sa";
	private static final String COMPONENT = "SA";
	private static final int FIXEDVALUE = 9999;
	private SADAO saDAO; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersistenceWithSA() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** {@inheritDoc} */
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
            saDAO = new SADAO(ds);
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void displayTable(HttpServletResponse response) throws SQLException, IOException {
    	response.setIntHeader("Refresh", 5);
    	
    	// Append table that lists all persons
        List<SA> resultList = saDAO.selectAllEntries();
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
     
        // Add [qm assign] button
    	response.getWriter().println("<p><center> Click here &#8594;  <input type=\"submit\" onclick=\"return window.prompt('Copy to clipboard: ','[qm assign]')\" value=\"[qm assign]\"></center></p>");
        
        int saIncidents = 0;
        for (SA sa : resultList) {        	
        	// Hitomi = 5, JohnH = 6
        	if (sa.getId() == 5 || sa.getId()== 6) {
        		// Calculate incident number
        		saIncidents += sa.getSa();
        		
        		// Get score
	        	String score = "0";
	        	
        		DecimalFormat df = new DecimalFormat("#.###");
        		score = df.format(((double)sa.getSum()) / QueueDays.hash.get(sa.getName()));
        	
	        	
	        	String pop = sa.getName() + " hass been +1, please go for assign.";
	        	String link = "<td><center><form action=\"" + LINKNAME + "?Id="+ sa.getId() + "&operation=add\" method=\"post\">" + "<input type=\"submit\" onclick=\"return window.prompt('" + pop + " Copy to clipboard: Ctrl+C, Enter','" + sa.getiNumber() + "')\" value=\"Add\" />" + "</form></center></td>";
	        	
	        	if (sa.getSum() < FIXEDVALUE) {
	        		response.getWriter().println("<tr><td height=\"30\"><center>" + (index++) + "</center></td>");
		        	if (index == 2) {
		        		response.getWriter().println("<td height=\"30\"><center><mark><b>" + xssEncoder.encodeHTML(sa.getName()+" ("+sa.getiNumber()+")") + "</b></mark></center></td>");
		        	} else {
		        		response.getWriter().println("<td height=\"30\"><center>" + xssEncoder.encodeHTML(sa.getName()+" ("+sa.getiNumber()+")") + "</center></td>");
		        	}
	        		response.getWriter().println(link); 
	        		response.getWriter().println("<td><center><form action=\"" + LINKNAME + "?Id="+ sa.getId() + "&operation=decrease\" method=\"post\">" + "<input type=\"submit\" value=\"Delete\" />" + "</form></center></td>"); 
		        	response.getWriter().println("<td height=\"30\"><center>" + sa.getSa() + "</center></td>");
					response.getWriter().println("<td height=\"30\"><center>" + sa.getSum() + "</center></td>" + "<td height=\"30\"><center>" + score + "</center></td>");
	        	} else {
		        	response.getWriter().println("<tr><td height=\"30\"><center>" + (index++) + "</center></td>");
		        	response.getWriter().println("<td height=\"30\"><center>" + xssEncoder.encodeHTML(sa.getName() + ": UNAVAILABLE") + "</center></td>");
		        	response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>"); 
		        	response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>"); 
		        	response.getWriter().println("<td height=\"30\"><center>" + sa.getSa() + "</center></td>");
					response.getWriter().println("<td height=\"30\"><center>" + (sa.getSum()-FIXEDVALUE) + "</center></td>");
				}
	        	
				response.getWriter().println("</tr>");
        	}
        }
        response.getWriter().println("</table></center></p>");
		
		response.getWriter().println("<p><center>SA has <mark>" + saIncidents + "</mark> incidents" + "</center></p>");
		response.getWriter().println("</body>");
        	     
		// Home button
		response.getWriter().println("<p><center><form action=\"" + "nw" + "\" method=\"get\">" + "<input type=\"submit\" value=\"Return to Home\" />" + "</form></center></p>");
        	
        
    }
    
    @Override
    protected void doIncrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // Extract name of person to be added from request
        String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = saDAO.getAmount(COMPONENT, ID) + 1;
        	saDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
    }

	@Override
	protected void doDecrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = saDAO.getAmount(COMPONENT, ID) - 1;
        	saDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
	}

	@Override
	protected void doReset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		saDAO.resetIncidentToAll(COMPONENT);
		
		response.sendRedirect(LINKNAME);
	}

	@Override
	protected void doUndo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = saDAO.getAmount(COMPONENT, ID) - FIXEDVALUE;
        	saDAO.updateIncidentToPerson(id, amount, COMPONENT);
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
        	int amount = saDAO.getAmount(COMPONENT, ID) + FIXEDVALUE;
        	saDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
	}

	@Override
	protected String getComponent() {
		// TODO Auto-generated method stub
		return "SA";
	}

}
