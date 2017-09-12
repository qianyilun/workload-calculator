package com.allen.nw;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import com.sap.cloud.sample.persistence.Person;

public class NWDAO {
	private DataSource dataSource;
	
	public NWDAO(DataSource newDataSource) throws SQLException {
        setDataSource(newDataSource);
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
                setEntry();
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
     * Create the person table.
     */
    private void createTable(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection
                .prepareStatement("CREATE TABLE NW "
                        + "(ID VARCHAR(255) PRIMARY KEY NOT NULL, "
                        + "FIRSTNAME VARCHAR (255),"
                        + "LASTNAME VARCHAR (255),"
                        + "AMOUNT INT," 
                        + "TOTAL INT)");
        pstmt.executeUpdate();
    }
    
    /**
     * Add one incident with personal information to the table.
     */
    public void addIncidentToPerson(NW nw) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("UPDATE NW "
                					+ "SET AMOUNT=?"
                					+ "WHERE FIRSTNAME=? AND LASTNAME=?");
            pstmt.setInt(1, nw.getAmount());
            pstmt.setString(2, nw.getPerson().getFirstName());
            pstmt.setString(3, nw.getPerson().getLastName());
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
    public void getIncidentOfPerson(Person person) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT AMOUNT "
                    				+ "FROM NW"
                    				+ "WHERE FIRSTNAME =? AND LASTNAME =?");
            pstmt.setString(1, person.getFirstName());
            pstmt.setString(2, person.getLastName());
            pstmt.executeUpdate();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
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
                nw.setFirstName(rs.getString(2));
                nw.setLastName(rs.getString(3));
                nw.setAmount(rs.getInt(4));
                nw.setTotal(rs.getInt(5));
                list.add(nw);
            }
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
    public void setEntry() throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("INSERT INTO NW (ID, FIRSTNAME, LASTNAME, AMOUNT, TOTAL) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2, "Allen");
            pstmt.setString(3, "Qian");
            pstmt.setInt(4, 10);
            pstmt.setInt(5, 20);
            pstmt.executeUpdate();
            System.out.println("Allen insert successful!");
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

}
