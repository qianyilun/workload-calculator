package com.allen.fc;

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
 * Data access object encapsulating all JDBC operations for FC.
 * 
 * @author Allen Qian
 */
public class FCDAO extends TemplateDAO{
	private static final String COMPONENT = "FC";
	public FCDAO(DataSource newDataSource) throws SQLException {
		super(newDataSource);
		// TODO Auto-generated constructor stub
	}

	/**
     * Get all entries from the table.
     */
    public List<FC> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT ID, NAME," + COMPONENT + ",TOTAL "
                    				+ "FROM ROOT");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<FC> list = new ArrayList<FC>();
            while (rs.next()) {
                FC fc = new FC();
                fc.setId(new Integer(rs.getInt(1)));
                
                fc.setSum(super.getSum(fc.getId()));
                
                
                fc.setName(rs.getString(2));
                fc.generateINumber();
                
                fc.setFc(rs.getInt(3));
                fc.setTotal(rs.getInt(4));
                list.add(fc);
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
