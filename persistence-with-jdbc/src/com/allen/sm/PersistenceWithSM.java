package com.allen.sm;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.allen.template.PersistenceWithTemplate;
import com.allen.template.TemplateDAO;
import com.sap.security.core.server.csi.IXSSEncoder;
import com.sap.security.core.server.csi.XSSEncoder;

/**
 * Servlet implementation class PersistencyWithSM
 */
public class PersistenceWithSM extends PersistenceWithTemplate {
	private static final long serialVersionUID = 1L;
	private SMDAO smDAO; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PersistenceWithSM() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** {@inheritDoc} */
    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
            smDAO = new SMDAO(ds,"SM");
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void displayTable(HttpServletResponse response) throws SQLException, IOException {
        // Append table that lists all persons
        List<SM> resultList = smDAO.selectAllEntries();
        response.getWriter().println(
                "<p><table width=70% border=\"1\"><tr><th colspan=\"1\"></th>" + "<th colspan=\"3\">" + (resultList.isEmpty() ? "" : resultList.size() + " ")
                        + "Entries in the Database</th>"
                        + "<th colspan=\"3\">" + "Smart Sorted</th></tr>");
        if (resultList.isEmpty()) {
            response.getWriter().println("<tr><td colspan=\"4\">Database is empty</td></tr>");
        } else {
            response.getWriter().println("<tr><th>#</th><th>Name</th><th>Increase</th><th>Decrease</th><th>Amount</th><th>Total</th><th>Score</th></tr>");
        }
        IXSSEncoder xssEncoder = XSSEncoder.getInstance();
        int index = 1;
        Collections.sort(resultList);
        for (SM sm : resultList) {
        	response.getWriter().println(
                    "<tr><td height=\"30\"><center>" + (index++) + "</center></td>"
                    + "<td height=\"30\"><center>" + xssEncoder.encodeHTML(sm.getName()) + "</center></td>"
					+ "<td><center><form action=\"persistencewithms?Id="+ sm.getId() + "\"method=\"post\">" + "<input type=\"submit\" value=\"Add\" />" + "</form></center></td>" 
					+ "<td>" + "<center><input type=\"submit\" value=\"-\"></center>" + "</td>"
					+ "<td height=\"30\"><center>" + sm.getAmount() + "</center></td>" // need to change to xssEncoder for getAmount()?
					+ "<td height=\"30\"><center>" + sm.getTotal() + "</center></td>" // need to change to xssEncoder for getTotal()?
					+ "<td height=\"30\"><center>" + String.format("%.3f", (sm.getAmount()*0.8 + (sm.getTotal()-sm.getAmount())*0.2 + sm.getTotal()),2) + "</center></td>"
					+ "</tr>");
        }
        response.getWriter().println("</table></p>");
    }
    
    @Override
    protected void doIncrease(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // Extract name of person to be added from request
        String id = request.getParameter("Id");
        if (id != null && !id.trim().isEmpty()) {
        	int temp = Integer.parseInt(id);
        	int amount = smDAO.getAmount(temp) + 1;
        	smDAO.addIncidentToPerson(id, amount);
        }
    }

}
