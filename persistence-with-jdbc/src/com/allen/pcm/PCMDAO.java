package com.allen.pcm;

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
 * Data access object encapsulating all JDBC operations for PCM.
 * 
 * @author Allen Qian
 */
public class PCMDAO extends TemplateDAO{
	private static final String COMPONENT = "PCM";
	public PCMDAO(DataSource newDataSource) throws SQLException {
		super(newDataSource);
		// TODO Auto-generated constructor stub
	}

	/**
     * Get all entries from the table.
     */
    public List<PCM> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT ID, NAME," + COMPONENT + ",TOTAL "
                    				+ "FROM ROOT");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<PCM> list = new ArrayList<PCM>();
            while (rs.next()) {
                PCM pcm = new PCM();
                pcm.setId(new Integer(rs.getInt(1)));
                
                pcm.setSum(super.getSum(pcm.getId()));
                
                
                pcm.setName(rs.getString(2));
                pcm.generateINumber();
                pcm.setPcm(rs.getInt(3));
                pcm.setTotal(rs.getInt(4));
                list.add(pcm);
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
