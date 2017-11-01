package com.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("unused")
public class DataBaseAccess {
	private final String localHostDataSource = "jdbc:mysql://127.0.0.1:3306/test?user=javauser&password=0000&useSSL=false";
	private final String severDataSource = "jdbc:mysql://140.121.199.228:3306/test?user=javauser&password=0000&useSSL=false";
	private Connection conn = null;
	private Statement st;
	private ResultSet rs;
	private TargetTable table;

	public static enum TargetTable {
		USER, GAME, FAVORITE, MESSAGE
	};

	public DataBaseAccess() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		try {
			conn = DriverManager.getConnection(localHostDataSource);
		} catch (Exception e) {
			conn = DriverManager.getConnection(severDataSource);
		}
		st = conn.createStatement();
	}

	public void changeTable(TargetTable table) {
		this.table = table;
	}

	public void insert(String table, String value, String order) throws SQLException{
		st.execute("INSERT INTO " + table + "value");
	}
	
	public void select(){
		
	}
}
