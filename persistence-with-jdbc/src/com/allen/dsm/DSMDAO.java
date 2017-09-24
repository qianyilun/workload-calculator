package com.allen.dsm;

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
 * Data access object encapsulating all JDBC operations for DSM.
 * 
 * @author Allen Qian
 */
public class DSMDAO extends TemplateDAO{
	private static final String COMPONENT = "DSM";
	public DSMDAO(DataSource newDataSource) throws SQLException {
		super(newDataSource);
		// TODO Auto-generated constructor stub
	}

	/**
     * Get all entries from the table.
     */
    public List<DSM> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT ID, NAME," + COMPONENT + ",TOTAL "
                    				+ "FROM ROOT");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<DSM> list = new ArrayList<DSM>();
            while (rs.next()) {
                DSM dsm = new DSM();
                dsm.setId(new Integer(rs.getInt(1)));
                
//                dsm.setSum(super.getSum(DSM.getId()));
                
                
                dsm.setName(rs.getString(2));
                dsm.setDsm(rs.getInt(3));
                dsm.setTotal(rs.getInt(4));
                list.add(dsm);
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
