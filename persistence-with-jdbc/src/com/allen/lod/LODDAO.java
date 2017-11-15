package com.allen.lod;

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
 * Data access object encapsulating all JDBC operations for LOD.
 * 
 * @author Allen Qian
 */
public class LODDAO extends TemplateDAO{
	private static final String COMPONENT = "LOD";
	public LODDAO(DataSource newDataSource) throws SQLException {
		super(newDataSource);
		// TODO Auto-generated constructor stub
	}

	/**
     * Get all entries from the table.
     */
    public List<LOD> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT ID, NAME," + COMPONENT + ",TOTAL "
                    				+ "FROM ROOT");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<LOD> list = new ArrayList<LOD>();
            while (rs.next()) {
                LOD lod = new LOD();
                lod.setId(new Integer(rs.getInt(1)));
                
                lod.setSum(super.getSum(lod.getId()));
                
                
                lod.setName(rs.getString(2));
                lod.generateINumber();
                
                lod.setLod(rs.getInt(3));
                lod.setTotal(rs.getInt(4));
                list.add(lod);
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
