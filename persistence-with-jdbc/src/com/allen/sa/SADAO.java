package com.allen.sa;

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
 * Data access object encapsulating all JDBC operations for SA.
 * 
 * @author Allen Qian
 */

public class SADAO extends TemplateDAO{
	public SADAO(DataSource newDataSource, String tableName) throws SQLException {
		super(newDataSource, tableName);
		// TODO Auto-generated constructor stub
	}

	/**
     * Get all entries from the table.
     */
    public List<SA> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT * FROM SA");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<SA> list = new ArrayList<SA>();
            while (rs.next()) {
                SA sa = new SA();
                sa.setId(new Integer(rs.getInt(1)));
                sa.setName(rs.getString(2));
                sa.setAmount(rs.getInt(3));
                sa.setTotal(rs.getInt(4));
                list.add(sa);
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
