package com.Database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.*;
import java.util.*;

import com.Analysis.Segmentation.WordSegmentation;

public class DataBaseManager {
	final static String LocalDataSource = "jdbc:mysql://127.0.0.1:3306/bot?";
	final static String SeverDataSource = "jdbc:mysql://140.121.199.228:3306/bot?";
	
	final static String Table_Users = "USERS";
	final static String Col_UserID = "USER_ID";
	final static String Col_LastMsgTime = "LAST_MESSAGE_TIME";
	
	static DataBaseManager DBM = new DataBaseManager();
	static SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	Connection mConnection;
	Statement mStatement;

	public static enum TargetTable { USER, GAME, FAVORITE, MESSAGE };

	public DataBaseManager() {
		// 如果Local端的DB沒有開啟，就Access遠端的DB
		String login = getLoginInfo();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			mConnection = DriverManager.getConnection(LocalDataSource + login); 
		} catch (Exception e) { 
			try { mConnection = DriverManager.getConnection(SeverDataSource + login); } 
			catch (SQLException ee) { ee.printStackTrace(); } 
		}
		
		try { mStatement = mConnection.createStatement(); }
		catch (SQLException e) { e.printStackTrace(); }
		
		// Test Output
		// mStatement.execute("SELECT * FROM USERS");
		// System.out.println(mStatement.getResultSet().getMetaData());
	}

	// 執行INSERT指令
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
	
	// 執行UPDATE指令
	public boolean update(String TABLE, String VALUE) {
		try {
			mStatement.execute("UPDATE " + TABLE + " SET " + VALUE);
			return true;
		} catch (SQLException e) { 
			e.printStackTrace();
			return false;
		}
	}

	// 執行SELECT指令，回傳ResultSet結構
	public ResultSet select(String TARGET_COL, String TARGET_TABLE) {
		return select(TARGET_COL, TARGET_TABLE, null, null);
	}
	
	public ResultSet select(String TARGET_COL, String TARGET_TABLE, String ORDER) {
		return select(TARGET_COL, TARGET_TABLE, null, ORDER);
	}
	
	public ResultSet select(String TARGET_COL, String TARGET_TABLE, String CONDITION, String ORDER) {
		String QUERY;
		try {
			QUERY = "SELECT " + TARGET_COL + " FROM " + TARGET_TABLE;
			QUERY += (CONDITION == null || CONDITION.trim().isEmpty() ? "" : (" WHERE " + CONDITION));
			QUERY += (ORDER == null || ORDER.trim().isEmpty() ? "" : (" ORDER BY " + ORDER));
			mStatement.execute(QUERY);
			return mStatement.getResultSet();
		} catch (Exception e) { 
			e.printStackTrace();
			return null;
		}
	}
	
	// 執行DELETE指令
	public boolean delete(String TABLE, String CONDITION) {
		try {
			mStatement.execute("DELETE FROM " + TABLE + " WHERE " + CONDITION);
			return true;
		} catch (Exception e) {
			System.err.println("[DB] DELETE " + TABLE + " WHERE " + CONDITION);
			e.printStackTrace();
			return false;
		}
	}
	
	// 新增新的使用者
	public static void addNewUser(String USER_ID) {
		DBM.insert(clipInsertTable(Table_Users, Col_UserID, Col_LastMsgTime), 
				"(" + clipSQT(USER_ID) + ", NOW())");
		WordSegmentation.addNewWord(USER_ID);
	}
	
	// 更新使用者資訊
	public static void updateUser(String USER_ID, String LAST_MSG_TIME) {
		DBM.update(Table_Users, Col_LastMsgTime + " = " + clipSQT(LAST_MSG_TIME) + 
				" WHERE " + Col_UserID + " = " + clipSQT(USER_ID));
	}
	
	// 刪除使用者資訊
	public static void deleteUser(String USER_ID) {
		DBM.delete(Table_Users, Col_UserID + " = " + clipSQT(USER_ID));
	}
	
	// 取得已知使用者清單
	public static ArrayList<String> getUserList() {
		ResultSet rs = DBM.select("*", Table_Users);
		if (rs == null) return null;
		
		try {
			ArrayList<String> arr = new ArrayList<>();
			while (rs.next()) 
				arr.add(rs.getString(Col_UserID));
			return arr;
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	// 將多個字串參數以單引號和括弧包起來
	public static String toDBString(String... args) {
		String str = "(" + clipSQT(args[0]); 
		for (int i = 1; i < args.length; i++)
			str += ", " + clipSQT(args[i]);
		return str + ")";
	}
	
	// 將Table與ColName變成Table(ColName1, ColName2, ...)的形式
	public static String clipInsertTable(String table, String... colName) {
		String s = table + "(" + colName[0];
		for (int i = 1; i < colName.length; i++)
			s += ", " + colName[i];
		return s + ")";
	}
	
	// 將字串以單引號包起來
	public static String clipSQT(String s) { return "\'" + s + "\'"; } 

	private static String getLoginInfo() {
		String s = "";
		try {
			BufferedReader mbr = new BufferedReader(new InputStreamReader(new FileInputStream("user.txt"), "UTF-8"));
			if (mbr.ready())
				s += "user=" + mbr.readLine();
			if (mbr.ready())
				s += "&password=" + mbr.readLine() + "&useSSL=false";
			mbr.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	
	
	public static void main(String[] args) {
		// unitTest();
		unitTest2();
	}
	
	public static void unitTest() throws SQLException {
		DataBaseManager db = new DataBaseManager();
		
		// String date = TimeFormat.format(new java.util.Date());
		
		db.insert("users(user_id)", "(\'Leon\')");
		ResultSet rs = db.select("*", Table_Users,"");
		
		while (rs.next()) 
			System.out.println(rs.getString(Col_UserID));
	}
	
	public static void unitTest2() {
		String user = "YeeMan";
		System.out.println("[WS] " + WordSegmentation.MaximumMatch(user));
		System.out.println("[DB] Insert " + user);
		addNewUser(user);
		logUserList();
		System.out.println("[WS] " + WordSegmentation.MaximumMatch(user));
		System.out.println("[DB] Delete " + user);
		deleteUser(user);
		logUserList();
	}
	
	public static void logUserList() {
		System.out.println("[DB] User List");
		for (String s: getUserList()) 
			System.out.println(s);
	}
}
