package com.BotModule;

import java.util.Arrays;

import com.Analysis.Matchers;
import com.Analysis.PatternMap;
import com.Analysis.Acting.ActingManager;
import com.Analysis.Acting.ActingMatch;
import com.Analysis.Response.ResponseManager;
import com.Analysis.Segmentation.WordSegmentation;

public class BotPenut {
	private static String WORD_ME = "我";
	private static String WORD_YOU = "[你妳您]";
	private static String TMP_REPLACE = "@@9999@@";
	
	public BotPenut() {
		
	}
	
	public String[] sendMessage(String msg) {
		while (true) {
			// 判斷是否Match到Pattern
			ActingMatch am = ActingManager.match(WordSegmentation.MaximumMatch(msg));
			if (am == null) break;
			
			// 取得Match到的主詞(@game)
			Matchers m = am.getMatchers();
			
			// 決定ResponseType
			String type = PatternMap.mathType(am.getType());
			
			String s = ResponseManager.getResponse(type, 0, m);
			return new String[] { s };
		}
		
		return defaultResponse(msg);
	}
	
	@SuppressWarnings("unused")
	private String[] tmpTesting(String msg) {
		if (msg.startsWith("-"))
			return msg.substring(1).toLowerCase().split(" ");
		return null;
	}
	
	private String[] defaultResponse(String msg) {
		String[] response = new String[1];
		
		msg = msg.replaceAll(WORD_ME, TMP_REPLACE);
		msg = msg.replaceAll(WORD_YOU, WORD_ME);
		msg = msg.replaceAll(TMP_REPLACE, "你");
		
		response[0] = String.format("為什麼你覺得%s?", msg.replaceAll(" ", "").replace("你覺得", ""));
		
		return response;
	}
	
	public static void main(String[] args) {
		BotPenut Penut = new BotPenut();
		for (int i = 0; i < 10; i++)
		System.out.println(Arrays.toString(Penut.sendMessage("我覺得貓咪大戰爭未來篇好難")));
	}
}
