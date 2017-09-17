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

public class FCDAO extends TemplateDAO{
	public FCDAO(DataSource newDataSource, String tableName) throws SQLException {
		super(newDataSource, tableName);
		// TODO Auto-generated constructor stub
	}

	/**
     * Get all entries from the table.
     */
    public List<FC> selectAllEntries() throws SQLException {
        Connection connection = super.getDataSource().getConnection();
        try {
            PreparedStatement pstmt = connection
                    .prepareStatement("SELECT * FROM FC");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<FC> list = new ArrayList<FC>();
            while (rs.next()) {
                FC fc_ea_ic_fim = new FC();
                fc_ea_ic_fim.setId(new Integer(rs.getInt(1)));
                fc_ea_ic_fim.setName(rs.getString(2));
                fc_ea_ic_fim.setAmount(rs.getInt(3));
                fc_ea_ic_fim.setTotal(rs.getInt(4));
                list.add(fc_ea_ic_fim);
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
