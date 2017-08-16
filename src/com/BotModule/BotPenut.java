package com.BotModule;

public class BotPenut {
	private static String ME = "我";
	private static String YOU = "你";
	private static String TMP_REPLACE = "@@2135";
	
	public static String[] response(String msg) {
		String[] s = new String[1];
		
		msg = msg.replaceAll(ME, TMP_REPLACE);
		msg = msg.replaceAll(YOU, ME);
		msg = msg.replaceAll(TMP_REPLACE, YOU);
		
		
		s[0] = String.format("為什麼你覺得%s?", msg);
		
		return s;
	}
}
