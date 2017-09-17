package com.allen.sm;

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
 * Data access object encapsulating all JDBC operations for SM.
 * 
 * @author Allen Qian
 */
public class SMDAO extends TemplateDAO{
	public SMDAO(DataSource newDataSource, String tableName) throws SQLException {
		super(newDataSource, tableName);
		// TODO Auto-generated constructor stub
	}

	/**
     * Get all entries from the table.
     */
    public List<SM> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT * FROM SM");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<SM> list = new ArrayList<SM>();
            while (rs.next()) {
                SM sm = new SM();
                sm.setId(new Integer(rs.getInt(1)));
                sm.setName(rs.getString(2));
                sm.setAmount(rs.getInt(3));
                sm.setTotal(rs.getInt(4));
                list.add(sm);
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
