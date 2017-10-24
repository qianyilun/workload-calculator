package com.allen.fc;

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
 * Servlet implementation class PersistencyWithFC
 */
public class PersistenceWithFC extends PersistenceWithTemplate {
	private static final long serialVersionUID = 1L;
	private static final String LINKNAME = "fc";
	private static final String COMPONENT = "FC";
	private static final int FIXEDVALUE = 9999;
	private FCDAO fcDAO; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersistenceWithFC() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** {@inheritDoc} */
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
            fcDAO = new FCDAO(ds);
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
        List<FC> resultList = fcDAO.selectAllEntries();
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

        for (FC fc : resultList) {        	
        	// Marc = 10, Yvonne = 13
        	if (fc.getId() == 10 || fc.getId()== 13) {
        	
        		// Get score
	        	String score = "0";
	        	if (fc.getFc() != 0) {
	        		DecimalFormat df = new DecimalFormat("#.###");
	        		score = df.format(((double)fc.getSum()) / QueueDays.hash.get(fc.getName()));
	        	}
	        	
	        	String pop = fc.getName() + " hass been +1, please go for assign.";
	        	String link = "<td><center><form action=\"" + LINKNAME + "?Id="+ fc.getId() + "&operation=add\" method=\"post\">" + "<input type=\"submit\" onclick=\"return window.prompt('" + pop + " Copy to clipboard: Ctrl+C, Enter','" + fc.getiNumber() + "')\" value=\"Add\" />" + "</form></center></td>";
	        	
	        	if (fc.getSum() < FIXEDVALUE) {
	        		response.getWriter().println("<tr><td height=\"30\"><center>" + (index++) + "</center></td>");
		        	if (index == 2) {
		        		response.getWriter().println("<td height=\"30\"><center><mark><b>" + xssEncoder.encodeHTML(fc.getName()+" ("+fc.getiNumber()+")") + "</b></mark></center></td>");
		        	} else {
		        		response.getWriter().println("<td height=\"30\"><center>" + xssEncoder.encodeHTML(fc.getName()+" ("+fc.getiNumber()+")") + "</center></td>");
		        	}
	        		response.getWriter().println(link); 
	        		response.getWriter().println("<td><center><form action=\"" + LINKNAME + "?Id="+ fc.getId() + "&operation=decrease\" method=\"post\">" + "<input type=\"submit\" value=\"Delete\" />" + "</form></center></td>"); 
		        	response.getWriter().println("<td height=\"30\"><center>" + fc.getFc() + "</center></td>");
					response.getWriter().println("<td height=\"30\"><center>" + fc.getSum() + "</center></td>" + "<td height=\"30\"><center>" + score + "</center></td>");
//		        	response.getWriter().println("<td height=\"30\"><center>" + fc.getSum() + "</center></td>");
		        	response.getWriter().println("<td><center><form action=\"" + LINKNAME + "?Id="+ fc.getId() + "&operation=ignore\" method=\"post\">" + "<input type=\"submit\" onclick=\"return window.confirm('This person will be unavailable and you can undo anytime!')\" value=\"unavailable\" />" + "</form></center></td>");
	        	} else {
		        	response.getWriter().println("<tr><td height=\"30\"><center>" + (index++) + "</center></td>");
		        	response.getWriter().println("<td height=\"30\"><center>" + xssEncoder.encodeHTML(fc.getName() + ": UNAVAILABLE") + "</center></td>");
		        	response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>"); 
		        	response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>"); 
		        	response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>");
					response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>");
					response.getWriter().println("<td><center><form action=\"" + LINKNAME + "?Id="+ fc.getId() + "&operation=undo\" method=\"post\">" + "<input type=\"submit\" value=\"undo\" />" + "</form></center></td>");
	        	}
	        	
				response.getWriter().println("</tr>");
        	}
        }
		response.getWriter().println("</table></center></p></body>");
        	     
		// Home button
		response.getWriter().println("<p><center><form action=\"" + "nw" + "\" method=\"get\">" + "<input type=\"submit\" value=\"Return to Home\" />" + "</form></center></p>");
        	
        
    }
    
    @Override
    protected void doIncrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // Extract name of person to be added from request
        String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = fcDAO.getAmount(COMPONENT, ID) + 1;
        	fcDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
    }

	@Override
	protected void doDecrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = fcDAO.getAmount(COMPONENT, ID) - 1;
        	fcDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
	}

	@Override
	protected void doReset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		fcDAO.resetIncidentToAll(COMPONENT);
		
		response.sendRedirect(LINKNAME);
	}

	@Override
	protected void doUndo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = fcDAO.getAmount(COMPONENT, ID) - FIXEDVALUE;
        	fcDAO.updateIncidentToPerson(id, amount, COMPONENT);
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
        	int amount = fcDAO.getAmount(COMPONENT, ID) + FIXEDVALUE;
        	fcDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
	}

	@Override
	protected String getComponent() {
		// TODO Auto-generated method stub
		return "FC/EA/IC/FIM";
	}

}
