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
    	response.getWriter().println("<title>QM Web</title>");
    	response.getWriter().println("<body><h1><center>" + getComponent() + "</center></h1>");
        try {
        	displayTable(response);
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
    }   
    
    protected abstract String getComponent();
    
    protected String checkRCC() {
		// TODO Auto-generated method stub
    	Calendar c = Calendar.getInstance();
    	int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
    	
    	if (timeOfDay == 15 || timeOfDay == 20) {
    		return "<center>Please check RCC before dispatching...</center>";
    	}
    	
    	return "";
	}

	/** {@inheritDoc} */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
    	
    	String operation = request.getParameter("operation");
    	
    	try {
    		if (operation != null) {
	    		if (operation.toLowerCase().equals("reset")) {
	    			doReset(request, response);
	    		} else if (operation.toLowerCase().equals("add")){
	    			doIncrease(request, response);
	    		} else if (operation.toLowerCase().equals("decrease")) {
	    			doDecrease(request, response);
	    		} else if (operation.toLowerCase().equals("ignore")) {
	    			doIgnore(request, response);
	    		} else if (operation.toLowerCase().equals("undo")) {
	    			doUndo(request, response);
	    		}
    		}
			doGet(request, response);
		} catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
    }
    
    protected abstract void doUndo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException;

    protected abstract void doIgnore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException;

	protected abstract void doDecrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException;

	protected abstract void doReset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException;

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
