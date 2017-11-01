package com.allen;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Log
 */
public class Log extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Log() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().println("<h1>What's new<h1>");
		
		response.getWriter().println("<h2>version_1.1.0</h2>");
		response.getWriter().println("<p>" + version_1_1_0 + "</p>");
		
		response.getWriter().println("<h2>version_1.1.1</h2>");
		response.getWriter().println("<p>" + version_1_1_1 + "</p>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String version_1_1_0 = ""
			+ "1.Maintained week 44 \"Queue Days\" <br />" + 
			"2.Number display issue fixed <br />" + 
			"3.Bug fixes and improvements <br />" + 
			"";

	private String version_1_1_1 = ""
			+ "1.Reduce the auto refresh frequency <br />"
			+ "2.Display the number of all incidents <br />"
			+ "3.Display the number of incidents for each component <br />"
			+ "4.'Amount and Total' of unavailable engineers are able to see now";
}
