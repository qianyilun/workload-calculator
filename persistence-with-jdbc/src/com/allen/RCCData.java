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
	        	
	        response.getWriter().println("<tr><th>#</th><th>Name</th><th>Current Queue Days</th><th>Increase</th><th>Decrease</th><th>%usage</th></tr>");	
		
	        		
			
			
			writeRow(index, response, "Alex", "1.00");
			writeRow(index, response, "Allen", "1.00");
			writeRow(index, response, "April", "1.00");
			writeRow(index, response, "Graham", "1.00");
			writeRow(index, response, "Hitomi", "1.00");
			writeRow(index, response, "John H", "1.00");
			writeRow(index, response, "John L", "0.5");
			writeRow(index, response, "Julie", "1.00");
			writeRow(index, response, "Leila", "1.00");
			writeRow(index, response, "Marc", "1.00");
			writeRow(index, response, "Pedro", "1.00");
			writeRow(index, response, "Stefan", "1.00");
			writeRow(index, response, "Yvonne", "0.75");
	    	
	    	
	    	
	    	
			
		
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
  
    	response.getWriter().println("<td><center><form action=\"" + "rccdata" + "?name="+ name + "&operation=increase\" method=\"post\">" + "<input type=\"submit\" value=\"Add\" />" + "</form></center></td>"); 
		
    	
		response.getWriter().println("<td><center><form action=\"" + "rccdata" + "?name="+ name + "&operation=decrease\" method=\"post\">" + "<input type=\"submit\" value=\"Delete\" />" + "</form></center></td>"); 
		
    	

    	response.getWriter().println("<td height=\"30\"><center>" + usage + "</center></td>");
    	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String operation = request.getParameter("operation");
		
		if (operation.equals("decrease")) {
			QueueDays.minusValue(name);
		} else if (operation.equals("increase")) {
			QueueDays.addValue(name);
		}

		doGet(request, response);
		
	}

}
