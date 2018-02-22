package com.BotModule;

import com.Analysis.*;
import com.Analysis.Acting.*;
import com.Analysis.Response.*;
import com.Analysis.Segmentation.WordSegmentation;
import static com.Library.LibraryUtil.logln;

public class BotFries {
	private static String WORD_ME = "我";
	private static String WORD_YOU = "[你妳您]";
	private static String TMP_REPLACE = "@@9999@@";
	
	public String[] sendMessage(String msg) {
		// 取得訊息的斷詞資訊
		logln("[Bot] Receive Raw: " + msg);
		String wSegMsg = WordSegmentation.MaximumMatch(msg);
		logln("[Bot] Receive Seg: " + wSegMsg);
		
		// 判斷是否Match到Pattern
		ActingMatch am = ActingManager.match(wSegMsg);
		
		// 若否則回傳預設回答
		if (am == null) {
			logln("[Bot] Not match, return default response");
			return defaultResponse(msg);
		}
		
		// 取得Match到的主詞(@game)
		Matchers m = am.getMatchers();
		
		// 判斷是否為Method Response
		String actingType = am.getType();
		boolean isProcess = PatternMap.isProcess(actingType);
		logln("[Bot] Method Response: " + isProcess);
		
		// 取得ResponseType
		String responseType = PatternMap.matchType(actingType);
		logln("[Bot] Response Type: " + responseType);
		
		// 製作完整的Response
		String response;
		if (isProcess) response = ResponseProcess.doResponseProcess(responseType, m);
		else response = ResponseManager.getResponse(responseType, 0, m);
		logln("[Bot] Response Msg: " + response);
		
		return new String[] { response };
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
//		unitTest("我覺得貓咪大戰爭好難");
//		unitTest("我覺得貓咪大戰爭不是很簡單");
		unitTest("聽說有個從天而降的語法");
		unitTest("最近Gorogoa有在特價嗎?");
		unitTest("最近Grand Theft Auto有在特價嗎?");
	}
	
	public static void unitTest(String msg) {
		BotFries Fries = new BotFries();
		// for (int i = 0; i < 3; i++)
			Fries.sendMessage(msg);
	}
}
