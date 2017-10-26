package com.data.jdba;

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
	
	public static enum targetTable{USER, GAME, FAVORITE, MESSAGE};
	
	public DataBaseAccess() throws ClassNotFoundException, SQLException{
		 Class.forName("com.mysql.jdbc.Driver");
		 conn = DriverManager.getConnection(datasource);
		 st = conn.createStatement();
	}
	
 
	
}
