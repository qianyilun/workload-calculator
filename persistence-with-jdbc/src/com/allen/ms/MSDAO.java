package com.allen.ms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.allen.template.TemplateDAO;

/**
 * Data access object encapsulating all JDBC operations for MS.
 * 
 * @author Allen Qian
 */
public class MSDAO extends TemplateDAO{
	private static final String COMPONENT = "MS";
	public MSDAO(DataSource newDataSource) throws SQLException {
		super(newDataSource);
		// TODO Auto-generated constructor stub
	}

	/**
     * Get all entries from the table.
     */
    public List<MS> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT ID, NAME," + COMPONENT + ",TOTAL "
                    				+ "FROM ROOT");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<MS> list = new ArrayList<MS>();
            while (rs.next()) {
                MS ms = new MS();
                ms.setId(new Integer(rs.getInt(1)));
                
                ms.setSum(super.getSum(ms.getId()));
                
                
                ms.setName(rs.getString(2));
                ms.setMs(rs.getInt(3));
                ms.setTotal(rs.getInt(4));
                list.add(ms);
            }
            Collections.sort(list);;
            return list;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

	
}
