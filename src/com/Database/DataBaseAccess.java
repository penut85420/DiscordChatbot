package com.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("unused")
public class DataBaseAccess {
	private final String datasource = "jdbc:mysql://1.171.32.6:3307/test?user=javauser&password=0000&useSSL=false";;
	private Connection conn = null;
	private Statement st;
	private ResultSet rs;
	private TargetTable table;

	public static enum TargetTable {
		USER, GAME, FAVORITE, MESSAGE
	};

	public DataBaseAccess() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(datasource);
		st = conn.createStatement();
	}

	public void changeTable(TargetTable table) {
		this.table = table;
	}

	class InsertAdapter {
		public InsertAdapter() {

		}

		public void insert() {

		}
	}
}
