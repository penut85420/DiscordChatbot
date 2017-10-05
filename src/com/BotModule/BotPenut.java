package com.BotModule;

public class BotPenut {
	private static String WORD_ME = "我";
	private static String WORD_YOU = "[你妳您]";
	private static String TMP_REPLACE = "@@9999@@";
	
	public BotPenut() {
		
	}
	
	public String[] sendMessage(String msg) {
		// 判斷是否Match到Pattern
		// 取得Match到的PatternType
		// 取得Match到的主詞(@game)
		// 決定ResponseType
		String[] seg = tmpTesting(msg);
		String matchType = "gamedifficult";
		
		if (seg != null) {
			String game = seg[1];
			if (seg[0].equals(matchType))
				return new String[] {"你覺得" + game + "很難嗎?"};
		}
		
		return defaultResponse(msg);
	}
	
	private String[] tmpTesting(String msg) {
		if (msg.startsWith("-"))
			return msg.substring(1).toLowerCase().split(" ");
		return null;
	}
	
	private String[] defaultResponse(String msg) {
		String[] response = new String[1];
		
		msg = msg.replaceAll(WORD_ME, TMP_REPLACE);
		msg = msg.replaceAll(WORD_YOU, WORD_ME);
		msg = msg.replaceAll(TMP_REPLACE, WORD_YOU);
		
		response[0] = String.format("為什麼你覺得%s?", msg);
		
		return response;
	}
}
