package com.allen.template;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.allen.NameHashTable;

/**
 * Data access object encapsulating all JDBC operations for Template.
 * 
 * @author Allen Qian
 */

public class TemplateDAO {
	private DataSource dataSource;
	
	public TemplateDAO(DataSource newDataSource) throws SQLException {
        setDataSource(newDataSource);
        NameHashTable.initHash();
    }
	
	/**
     * Get data source which is used for the database operations.
     */
    public DataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * Set data source to be used for the database operations.
     */
    public void setDataSource(DataSource newDataSource) throws SQLException {
        this.dataSource = newDataSource;
        checkTable();
    }
    
    /**
     * Check if the table already exists and create it if not.
     */
    private void checkTable() throws SQLException {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            if (!existsTable(connection)) {
                createTable(connection);
                
                /*
                 * TEST ONLY
                 */
                setEntry("Allen", 0, 0, 10, 0, 0, 0, 0, 0, 20);
                setEntry("Julie", 0, 0, 4, 0, 0, 0, 0, 0, 6);
                setEntry("Alex", 0, 0, 8, 0, 0, 0, 0, 0, 21);
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    /**
     * Check if the table already exists.
     */
    private boolean existsTable(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getTables(null, null, "ROOT", null);
        while (rs.next()) {
            String name = rs.getString("TABLE_NAME");
            if (name.equals("ROOT")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create the table.
     */
    private void createTable(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection
                .prepareStatement("CREATE TABLE ROOT "
                        + " (ID INT PRIMARY KEY NOT NULL, "
                        + "NAME VARCHAR (255),"
                        + "NW INT,"
                        + "MS INT,"
                        + "SM INT,"
                        + "DSM INT,"
                        + "FC INT,"
                        + "LOD INT,"
                        + "PCM INT,"
                        + "SA INT," 
                        + "TOTAL INT)");
        pstmt.executeUpdate();
        
        pstmt.close();
        
        
        Statement sm = connection.createStatement();
        sm.executeQuery("CREATE VIEW VW_ROOT AS " + 
		        		"SELECT ID, " + 
		        		"  NW + MS + SM + DSM + FC + LOD + PCM + SA  AS SUM"
		        		+ " FROM ROOT");	
        
        sm.close();
    }
    
    /**
     * Add one incident with personal information to the table.
     */
    public void addIncidentToPerson(String id, int amount, String component) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("UPDATE ROOT"
                					+ " SET " + component + "=?"
                					+ " WHERE Id=" + id + "");
            pstmt.setInt(1, amount);
            pstmt.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    /**
     * Get the number of incident for a specific component in the table.
     */
    public int getAmount(String component, int id) throws SQLException {
        Connection connection = dataSource.getConnection();
        ResultSet rs = null;
        
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT " + component
                    				+ " FROM ROOT"
                    				+ " WHERE ID=?");
            pstmt.setInt(1, id);
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
     * Get the total number (SUM) of incident for a specific person in the table.
     */
    public int getSum(int id) throws SQLException {
        Connection connection = dataSource.getConnection();
        ResultSet rs = null;
        
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT SUM"
                    				+ " FROM VW_ROOT"
                    				+ " WHERE ID=?");
            pstmt.setInt(1, id);
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

    
    /*
     * Test purpose ONLY
     */
    /**
     * Add one incident with personal information to the table.
     */
    public void setEntry(String name, int nw, int ms, int sm, int dsm, int fc, int lod, 
    		int pcm, int sa, int total) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("INSERT INTO ROOT "
                    		+ " (ID, NAME, NW, MS, SM, DSM, FC, LOD, PCM, SA, TOTAL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
            
           
            NameHashTable.initHash();
            pstmt.setInt(1, NameHashTable.hash.get(name).intValue());
            pstmt.setString(2, name);
            pstmt.setInt(3, nw);
            pstmt.setInt(4, ms);
            pstmt.setInt(5, sm);
            pstmt.setInt(6, dsm);
            pstmt.setInt(7, fc);
            pstmt.setInt(8, lod);
            pstmt.setInt(9, pcm);
            pstmt.setInt(10, sa);
            pstmt.setInt(11, total);
            pstmt.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

}
