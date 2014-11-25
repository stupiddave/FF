package com.dave.fantasyfootball.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class PropertiesServiceImpl implements PropertiesService {

	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public int getSelectionGameweek() {
		Connection conn = null;
		String sql = "SELECT value "
				+ "FROM properties_t"
				+ "WHERE name = 'selection_gameweek'";
		try {
		conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
