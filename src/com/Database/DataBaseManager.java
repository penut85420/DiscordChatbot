package com.Database;

import java.sql.*;
import java.text.*;
import java.util.*;

public class DataBaseManager {
	final static String LocalDataSource = "jdbc:mysql://127.0.0.1:3306/bot?user=newuser&password=971233&useSSL=false";
	final static String SeverDataSource = "jdbc:mysql://140.121.199.228:3306/bot?user=newuser&password=971233&useSSL=false";
	final static String UserTable = "USERS";
	
	static DataBaseManager DBM = new DataBaseManager();
	static SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	Connection mConnection;
	Statement mStatement;

	public static enum TargetTable { USER, GAME, FAVORITE, MESSAGE };

	public DataBaseManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mConnection = DriverManager.getConnection(LocalDataSource); 
		} catch (Exception e) { 
			try { mConnection = DriverManager.getConnection(SeverDataSource); } 
			catch (SQLException ee) { ee.printStackTrace(); } 
		}
		
		try { mStatement = mConnection.createStatement(); }
		catch (SQLException e) { e.printStackTrace(); }
		
		try {
			mStatement.execute("SELECT * FROM USERS");
			System.out.println(mStatement.getResultSet().getMetaData());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 執行INSERT指令，成功回傳true，失敗回傳false
	public boolean insert(String TABLE, String VALUE) {
		try { 
			mStatement.execute("INSERT INTO " + TABLE + " VALUE " + VALUE);
			return true;
		} catch (SQLException e) {
			System.err.println("[DB] INSERT INTO " + TABLE + " VALUE " + VALUE);
			e.printStackTrace(); 
			return false;
		}
	}
	
	// 執行UPDATE指令，成功回傳true，失敗回傳false
	public boolean update(String TABLE, String VALUE) {
		try {
			mStatement.execute("UPDATE " + TABLE + " SET " + VALUE);
			return true;
		} catch (SQLException e) { 
			e.printStackTrace();
			return false;
		}
	}

	public ResultSet select(String TARGET_COL, String TARGET_TABLE, String CONDITION, String ORDER) {
		try {
			mStatement.execute("SELECT " + TARGET_COL + "FROM" + TARGET_TABLE + " WHERE " + CONDITION + " ORDER BY " + ORDER);
			return mStatement.getResultSet();
		} catch (Exception e) { 
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet select(String TARGET_COL, String TARGET_TABLE, String ORDER) {
		try {
			String QUERY = "SELECT " + TARGET_COL + " FROM " + TARGET_TABLE;
			QUERY += (ORDER.isEmpty() ? "" : (" ORDER BY " + ORDER));
			mStatement.execute(QUERY);
			return mStatement.getResultSet();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 新增新的使用者
	public static void addNewUser(String user_id, String last_msg_time) {
		DBM.insert("users(user_id, last_message_time)", toDBString(user_id, last_msg_time));
	}
	
	// 更新使用者資訊
	public static void updateUser(String user_id, String last_msg_time) {
		DBM.update("USERS", "last_message_time = " + clipSQT(last_msg_time) + " WHERE user_id = " + clipSQT(user_id));
	}
	
	// 取得已知使用者清單
	public static ArrayList<String> getUserList() {
		ResultSet rs = DBM.select("*", "users", "");
		if (rs == null) return null;
		
		try {
			ArrayList<String> arr = new ArrayList<>();
			while (rs.next()) 
				arr.add(rs.getString("USER_ID") + rs.getObject("last_message_time"));
			return arr;
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	// 將多個字串參數以單引號和括弧包起來
	public static String toDBString(String... args) {
		String str = "(" + args[0]; 
		for (int i = 1; i < args.length; i++)
			str += ", " + clipSQT(args[i]);
		return str + ")";
	}
	
	// 將字串以單引號包起來
	public static String clipSQT(String s) { return "\'" + s + "\'"; } 

	public static void main(String[] args) {
		// unitTest();
		unitTest2();
	}
	
	public static void unitTest() throws SQLException {
		DataBaseManager db = new DataBaseManager();
		
		// String date = TimeFormat.format(new java.util.Date());
		
		db.insert("users(user_id)", "(\'Leon\')");
		ResultSet rs = db.select("*", "users","");
		
		while (rs.next()) System.out.println(rs.getString("user_id"));
	}
	
	public static void unitTest2() {
		updateUser("0035", "2017-11-30 00:00:00");
		for (String s: getUserList()) 
			System.out.println(s);
	}
}
