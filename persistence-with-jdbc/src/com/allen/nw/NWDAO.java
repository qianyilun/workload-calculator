package com.allen.nw;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import com.allen.NameHashTable;
import com.sap.cloud.sample.persistence.Person;

public class NWDAO {
	private DataSource dataSource;
	
	public NWDAO(DataSource newDataSource) throws SQLException {
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
     * Check if the NW table already exists and create it if not.
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
     * Check if the NW table already exists.
     */
    private boolean existsTable(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getTables(null, null, "NW", null);
        while (rs.next()) {
            String name = rs.getString("TABLE_NAME");
            if (name.equals("NW")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create the NW table.
     */
    private void createTable(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection
                .prepareStatement("CREATE TABLE NW "
                        + "(ID INT PRIMARY KEY NOT NULL, "
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
                    .prepareStatement("UPDATE NW "
                					+ "SET AMOUNT=?"
                					+ "WHERE Id=" + id + "");
            pstmt.setInt(1, amount);
            pstmt.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    /**
     * Get the number of NW incident for a person in the table.
     */
    public int getNWAmount(int id) throws SQLException {
        Connection connection = dataSource.getConnection();
        ResultSet rs = null;
        
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT AMOUNT "
                    				+ "FROM NW "
                    				+ "WHERE ID=?");
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
     * Get all entries from the table.
     */
    public List<NW> selectAllEntries() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT * FROM NW");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<NW> list = new ArrayList<NW>();
            while (rs.next()) {
                NW nw = new NW();
                nw.setId(new Integer(rs.getInt(1)));
                nw.setName(rs.getString(2));
                nw.setAmount(rs.getInt(3));
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
                    .prepareStatement("INSERT INTO NW (ID, NAME, AMOUNT, TOTAL) VALUES (?, ?, ?, ?)");
            
           
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
