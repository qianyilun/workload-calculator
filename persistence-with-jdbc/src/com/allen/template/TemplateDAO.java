package com.allen.template;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.allen.NameHashTable;

/**
 * Data access object encapsulating all JDBC operations for Template.
 * 
 * @author Allen Qian
 */

public class TemplateDAO {
	private DataSource dataSource;
	private String tableName;
	
	public TemplateDAO(DataSource newDataSource, String tableName) throws SQLException {
		this.tableName = tableName;
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
    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) throws SQLException {
        this.tableName = tableName;
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
                setEntry("Allen", 10, 20);
                setEntry("Julie", 4, 6);
                setEntry("Alex", 8, 21);
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
        ResultSet rs = meta.getTables(null, null, tableName, null);
        while (rs.next()) {
            String name = rs.getString("TABLE_NAME");
            if (name.equals(tableName)) {
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
                .prepareStatement("CREATE TABLE " + tableName
                        + " (ID INT PRIMARY KEY NOT NULL, "
                        + "NAME VARCHAR (255),"
                        + "AMOUNT INT," 
                        + "TOTAL INT)");
        pstmt.executeUpdate();
    }
    
    /**
     * Add one incident with personal information to the table.
     */
    public void addIncidentToPerson(String id, int amount) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("UPDATE " + tableName
                					+ " SET AMOUNT=?"
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
     * Get the number of incident for a person in the table.
     */
    public int getAmount(int id) throws SQLException {
        Connection connection = dataSource.getConnection();
        ResultSet rs = null;
        
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT AMOUNT "
                    				+ "FROM " + tableName
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
    public void setEntry(String name, int x, int y) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("INSERT INTO " + tableName
                    		+ " (ID, NAME, AMOUNT, TOTAL) VALUES (?, ?, ?, ?)");
            
           
            NameHashTable.initHash();
            pstmt.setInt(1, NameHashTable.hash.get(name).intValue());
            pstmt.setString(2, name);
            pstmt.setInt(3, x);
            pstmt.setInt(4, y);
            pstmt.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

}
