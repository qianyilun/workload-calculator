package com.allen.rtc;

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
 * Data access object encapsulating all JDBC operations for RTC.
 * 
 * @author Allen Qian
 */
public class RTCDAO extends TemplateDAO{
	private static final String COMPONENT = "RTC";
	public RTCDAO(DataSource newDataSource) throws SQLException {
		super(newDataSource);
		// TODO Auto-generated constructor stub
	}

	/**
     * Get all entries from the table.
     */
    public List<RTC> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT ID, NAME," + COMPONENT + ",TOTAL "
                    				+ "FROM ROOT");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<RTC> list = new ArrayList<RTC>();
            while (rs.next()) {
                RTC rtc = new RTC();
                rtc.setId(new Integer(rs.getInt(1)));
                
                rtc.setSum(super.getSum(rtc.getId()));
                
                
                rtc.setName(rs.getString(2));
                rtc.generateINumber();
                rtc.setRtc(rs.getInt(3));
                rtc.setTotal(rs.getInt(4));
                list.add(rtc);
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
