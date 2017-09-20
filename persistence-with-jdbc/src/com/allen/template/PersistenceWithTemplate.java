package com.allen.template;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.sample.persistence.PersistenceWithJDBCServlet;

/**
 * Servlet implementation class PersistenceWithMS
 */
public abstract class PersistenceWithTemplate extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceWithJDBCServlet.class);
	 private String tableName;


	 /** {@inheritDoc} */
    public abstract void init() throws ServletException;
	    
    /** {@inheritDoc} */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.getWriter().println("<h1><center>" + greetings() + "</center></h1>");
        try {
        	displayTable(response);
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
    }   
    
    // https://stackoverflow.com/questions/27589701/showing-morning-afternoon-evening-night-message-based-on-time-in-java
    private String greetings() {
		// TODO Auto-generated method stub
    	Calendar c = Calendar.getInstance();
    	int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

    	if(timeOfDay >= 0 && timeOfDay < 12){
    	    return "Good Morning!";        
    	}else if(timeOfDay >= 12 && timeOfDay < 16){
    	    return "Good Afternoon!";
    	}else if(timeOfDay >= 16 && timeOfDay < 21){
    	    return "Good Evening!";
    	}else if(timeOfDay >= 21 && timeOfDay < 24){
    	    return "Good Night!";
    	}
    	return "unknow time";
	}

	/** {@inheritDoc} */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
    	
    	String operation = request.getParameter("operator");
    	
    	try {
    		if (operation.toLowerCase().equals("reset")) {
    			doReset(request, response);
    		} else if (operation.toLowerCase().equals("add")){
    			doIncrease(request, response);
    		} else if (operation.toLowerCase().equals("decrease")) {
    			doDecrease(request, response);
    		}
			doGet(request, response);
		} catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
    }
    
    protected abstract void doDecrease(HttpServletRequest request, HttpServletResponse response);

	protected abstract void doReset(HttpServletRequest request, HttpServletResponse response);

	protected abstract void displayTable(HttpServletResponse response) throws SQLException, IOException;
    
    protected abstract void doIncrease(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, SQLException;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
