package com.allen;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allen.nw.NW;
import com.sap.security.core.server.csi.IXSSEncoder;
import com.sap.security.core.server.csi.XSSEncoder;

/**
 * Servlet implementation class RCCData
 */
public class RCCData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RCCData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().println("<html>");
		response.getWriter().println("<html>");
		response.getWriter().println("<head><title> Edit Availability</title></head>");
		response.getWriter().println("<body>");
		response.getWriter().println("<center><h1> Edit Availability </h1></center>");
		
		displayTable(response);
		
		response.getWriter().println("</body>");
		response.getWriter().println("</html>");
	}

	private void displayTable(HttpServletResponse response) {
		try {
			Integer index = new Integer(1);
			response.setIntHeader("Refresh", 120);
	    	
	    	response.getWriter().println(
	                "<p><center><table width=70% border=\"1\">");
	        	
	        response.getWriter().println("<tr><th>#</th><th>Name</th><th>Old Queue Days</th><th>New Queue Days</th><th>Update</th><th>%usage</th></tr>");	
		
	        		
			
			
			writeRow(index, response, "Alex", "1.00");
			   	
	    	
	    	
	    	
	    	
			
		
			response.getWriter().println("</tr></table></center></p>");
	        
	        response.getWriter().println("</body>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    
	}

	private void writeRow(Integer index, HttpServletResponse response, String name, String usage) throws IOException {
		// TODO Auto-generated method stub
		response.getWriter().println("<tr><td height=\"30\"><center>" + (index++) + "</center></td>");
		
		response.getWriter().println("<td height=\"30\"><center>" + name + "</center></td>");
    	response.getWriter().println("<td height=\"30\"><center>" + QueueDays.getValue(name) + "</center></td>");
    	
    	int value = 55;
    	response.getWriter().println("<td height=\"30\"><center>" + "input here" + "</center></td>");

    	response.getWriter().println("<td><center><form action=\"" + "rccdata" + "?Name="+ name + "&Value=" + value + "\" method=\"post\">" + "<input type=\"submit\" value=\"Delete\" />" + "</form></center></td>"); 
    	

    	response.getWriter().println("<td height=\"30\"><center>" + usage + "</center></td>");
    	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("Name");
		String value = request.getParameter("Value");
		
		QueueDays.changeValue(name, value);
		doGet(request, response);
		
	}

}
