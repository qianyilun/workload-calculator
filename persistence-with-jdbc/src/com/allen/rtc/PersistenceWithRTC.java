package com.allen.rtc;

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
 * Servlet implementation class PersistencyWithRTC
 */
public class PersistenceWithRTC extends PersistenceWithTemplate {
	private static final long serialVersionUID = 1L;
	private static final String LINKNAME = "rtc";
	private static final String COMPONENT = "RTC";
	private static final int FIXEDVALUE = 9999;
	private RTCDAO rtcDAO; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersistenceWithRTC() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** {@inheritDoc} */
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
            rtcDAO = new RTCDAO(ds);
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
        List<RTC> resultList = rtcDAO.selectAllEntries();
        response.getWriter().println(
                "<p><center><table width=70% border=\"1\"><tr><th colspan=\"1\"></th>" + "<th colspan=\"3\">" + (resultList.isEmpty() ? "" : resultList.size() + " ")
                        + "Employees in the EPM-BPC team</th>"
                        + "<th colspan=\"3\">" + "Smart Sorted</th></tr>");
        if (resultList.isEmpty()) {
            response.getWriter().println("<tr><td colspan=\"4\">Database is empty</td></tr>");
        } else {
            response.getWriter().println("<tr><th>#</th><th>Name</th><th>Assign</th><th>Remove</th><th>Amount</th><th>Total</th><th>AVG Q-DAY</th></tr>");
        }
        IXSSEncoder xssEncoder = XSSEncoder.getInstance();
        int index = 1;
        Collections.sort(resultList); 
     
        // Add [EPM_QM_ASSIGNED] button
    	response.getWriter().println("<p><center> Click here &#8594;  <input type=\"submit\" onclick=\"return window.prompt('Copy to clipboard: ','[EPM_QM_ASSIGNED]')\" value=\"[EPM_QM_ASSIGNED]\"></center></p>");
        
        int rtcIncidents = 0;
        for (RTC rtc : resultList) {        	
        	// JohnH = 6, Stefan = 12, Leila = 9, Julie = 8, Alex = 1, Graham = 4

        	if (rtc.getId() == 6 || rtc.getId()== 12
        			|| rtc.getId() == 9 || rtc.getId()== 8
        			|| rtc.getId() == 1 || rtc.getId()== 4) {
        		// Calculate incident number
        		rtcIncidents += rtc.getRtc();
        		
        		// Get score
	        	String score = "0";
	        	
        		DecimalFormat df = new DecimalFormat("#.###");
        		score = df.format(((double)rtc.getSum()) / QueueDays.hash.get(rtc.getName()));
        	
	        	
	        	String pop = rtc.getName() + " has been +1, please go for assign.";
	        	String link = "<td><center><form action=\"" + LINKNAME + "?Id="+ rtc.getId() + "&operation=add\" method=\"post\">" + "<input type=\"submit\" onclick=\"return window.prompt('" + pop + " Copy to clipboard: Ctrl+C, Enter','" + rtc.getiNumber() + "')\" value=\"Add\" />" + "</form></center></td>";
	        	
	        	if (rtc.getSum() < FIXEDVALUE) {
	        		response.getWriter().println("<tr><td height=\"30\"><center>" + (index++) + "</center></td>");
		        	if (index == 2) {
		        		response.getWriter().println("<td height=\"30\"><center><mark><b>" + xssEncoder.encodeHTML(rtc.getName()+" ("+rtc.getiNumber()+")") + "</b></mark></center></td>");
		        	} else {
		        		response.getWriter().println("<td height=\"30\"><center>" + xssEncoder.encodeHTML(rtc.getName()+" ("+rtc.getiNumber()+")") + "</center></td>");
		        	}
	        		response.getWriter().println(link); 
	        		response.getWriter().println("<td><center><form action=\"" + LINKNAME + "?Id="+ rtc.getId() + "&operation=decrease\" method=\"post\">" + "<input type=\"submit\" value=\"Delete\" />" + "</form></center></td>"); 
		        	response.getWriter().println("<td height=\"30\"><center>" + rtc.getRtc() + "</center></td>");
					response.getWriter().println("<td height=\"30\"><center>" + rtc.getSum() + "</center></td>" + "<td height=\"30\"><center>" + score + "</center></td>");
	        	} else {
		        	response.getWriter().println("<tr><td height=\"30\"><center>" + (index++) + "</center></td>");
		        	response.getWriter().println("<td height=\"30\"><center>" + xssEncoder.encodeHTML(rtc.getName() + ": UNAVAILABLE") + "</center></td>");
		        	response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>"); 
		        	response.getWriter().println("<td><center>"+ xssEncoder.encodeHTML("N/A") + "</center></td>"); 
		        	response.getWriter().println("<td height=\"30\"><center>" + rtc.getRtc() + "</center></td>");
					response.getWriter().println("<td height=\"30\"><center>" + (rtc.getSum()-FIXEDVALUE) + "</center></td>");
				}
	        	
				response.getWriter().println("</tr>");
        	}
        }
        response.getWriter().println("</table></center></p>");
		
		response.getWriter().println("<p><center>RTC has <mark>" + rtcIncidents + "</mark> incidents" + "</center></p>");
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
        	int amount = rtcDAO.getAmount(COMPONENT, ID) + 1;
        	rtcDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
    }

	@Override
	protected void doDecrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = rtcDAO.getAmount(COMPONENT, ID) - 1;
        	rtcDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
	}

	@Override
	protected void doReset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		rtcDAO.resetIncidentToAll(COMPONENT);
		
		response.sendRedirect(LINKNAME);
	}

	@Override
	protected void doUndo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int ID = Integer.parseInt(id);
        	int amount = rtcDAO.getAmount(COMPONENT, ID) - FIXEDVALUE;
        	rtcDAO.updateIncidentToPerson(id, amount, COMPONENT);
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
        	int amount = rtcDAO.getAmount(COMPONENT, ID) + FIXEDVALUE;
        	rtcDAO.updateIncidentToPerson(id, amount, COMPONENT);
        }
        
        response.sendRedirect(LINKNAME);
	}

	@Override
	protected String getComponent() {
		// TODO Auto-generated method stub
		return "RTC";
	}

}
