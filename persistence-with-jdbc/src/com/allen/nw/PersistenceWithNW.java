package com.allen.nw;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.sample.persistence.PersistenceWithJDBCServlet;
import com.sap.cloud.sample.persistence.Person;
import com.sap.cloud.sample.persistence.PersonDAO;
import com.sap.security.core.server.csi.IXSSEncoder;
import com.sap.security.core.server.csi.XSSEncoder;

/**
 * Servlet implementation class PersistenceWithNW
 */
public class PersistenceWithNW extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceWithJDBCServlet.class);

	 private NWDAO nwDAO;

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
	    
    /** {@inheritDoc} */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("<p>Persistence with JDBC NW table!</p>");
        try {
        	displayNWTable(response);
//            appendAddForm(response);
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
    }   
    
    /** {@inheritDoc} */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
    	
    	doGet(request, response);
//        try {
//            doAdd(request);
//            doGet(request, response);
//        } catch (Exception e) {
//            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
//            LOGGER.error("Persistence operation failed", e);
//        }
    }
    
    private void displayNWTable(HttpServletResponse response) throws SQLException, IOException {
        // Append table that lists all persons
        List<NW> resultList = nwDAO.selectAllEntries();
        response.getWriter().println(
                "<p><table border=\"1\"><tr><th colspan=\"3\">" + (resultList.isEmpty() ? "" : resultList.size() + " ")
                        + "Entries in the Database</th></tr>");
        if (resultList.isEmpty()) {
            response.getWriter().println("<tr><td colspan=\"3\">Database is empty</td></tr>");
        } else {
            response.getWriter().println("<tr><th>First name</th><th>Last name</th><th>Amount</th><th>Total</th></tr>");
        }
        IXSSEncoder xssEncoder = XSSEncoder.getInstance();
        for (NW nw : resultList) {
        	response.getWriter().println(resultList.size());
            response.getWriter().println(
                    "<tr><td>" + xssEncoder.encodeHTML(nw.getPerson().getFirstName()) + "</td>"
					+ "<td>" + xssEncoder.encodeHTML(nw.getPerson().getLastName()) + "</td>"
					+ "<td>" + nw.getAmount() + "</td>"
					+ "<td>" + nw.getTotal() + "</td>"
					+ "</tr>");
        }
        response.getWriter().println("</table></p>");
    }

}
