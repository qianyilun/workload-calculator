package com.allen.nw;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.allen.NameHashTable;
import com.allen.template.TemplateDAO;

/**
 * Data access object encapsulating all JDBC operations for NW.
 * 
 * @author Allen Qian
 */
public class NWDAO extends TemplateDAO{
	private static final String COMPONENT = "NW";
	public NWDAO(DataSource newDataSource) throws SQLException {
		super(newDataSource);
		// TODO Auto-generated constructor stub

        checkCounter();
	}
	
	

	/**
     * Get all entries from the table.
     */
    public List<NW> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT ID, NAME," + COMPONENT + ",TOTAL "
                    				+ "FROM ROOT");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<NW> list = new ArrayList<NW>();
            while (rs.next()) {
                NW nw = new NW();
                nw.setId(new Integer(rs.getInt(1)));
                
                nw.setSum(super.getSum(nw.getId()));
                
                
                nw.setName(rs.getString(2));
                nw.generateINumber();
                nw.setNw(rs.getInt(3));
                nw.setTotal(rs.getInt(4));
                list.add(nw);
            }
            Collections.sort(list);;
            return list;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    
    private void checkCounter() throws SQLException {
		// TODO Auto-generated method stub
    	Connection connection = null;

        try {
            connection = super.getDataSource().getConnection();
            if (!existsCounter(connection)) {
                createCounter(connection);
                
                /*
                 * TEST ONLY
                 */
                setCounter();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
	}

	private void setCounter() throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = super.getDataSource().getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("INSERT INTO COUNTER "
                    		+ " (TIMES) VALUES (?)");
            
            pstmt.setInt(1, 0);
            pstmt.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
	



	private void createCounter(Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = connection
                .prepareStatement("CREATE TABLE COUNTER "
                        + " (TIMES INT PRIMARY KEY NOT NULL)");
        pstmt.executeUpdate();
        
        pstmt.close();
        
  
	}

	private boolean existsCounter(Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		DatabaseMetaData meta = connection.getMetaData();
        ResultSet rs = meta.getTables(null, null, "COUNTER", null);
        while (rs.next()) {
            String name = rs.getString("TABLE_NAME");
            if (name.equals("COUNTER")) {
                return true;
            }
        }
        return false;
	}

	/**
     * Get the total counter (TIMES).
     */
    public int getTimes() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        ResultSet rs = null;
        
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT *"
                    				+ " FROM COUNTER");
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	return rs.getInt(1);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
		return -1;
    }

    /**
     * Add one more visit time to the total counter (TIMES).
     */
    public void addTimes() throws SQLException {
    	Connection connection = super.getDataSource().getConnection();
    	int times = getTimes()+1;
    	
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("UPDATE COUNTER"
                					+ " SET TIMES=" + times
                							+ " WHERE 1=1");
            pstmt.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
