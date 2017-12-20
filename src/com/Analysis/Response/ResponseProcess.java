package com.Analysis.Response;

import com.Analysis.Matchers;
import com.Analysis.WordList;
import com.InternetResource.GameNewsGNN;
import com.InternetResource.SteamDeal;

import static com.Analysis.Response.ResponseManager.getResponse;
import static com.Library.LibraryUtil.logln;

public class ResponseProcess {
	
	public static String doResponseProcess(String type, Matchers m) {
		logln("[ResponseProcess] getResponse - Type: " + type);
		try {
			Class<?>[] param = {Matchers.class};
			return (String) ResponseProcess.class.getMethod(type, param).invoke(null, m);
		} catch (Exception e) {
			logln("[ResponseProcess] Error Occur");
			e.printStackTrace(System.out);
		}
		return null;
	}
	
	public static String IsGameHasNews(Matchers m) {
		String game = m.get("game");
		if (game == null) return getResponse("WhatAreYouTalkingAbout");
		
		String news = GameNewsGNN.getNewsInfo(game);

		if (news == null) return getResponse("GameNoNews", 0, m); 
		
		String[] info = news.split(";");
		
		m.add("news_title", info[0]);
		m.add("news_link", info[1]);
		
		return getResponse("GameHasNews", 0, m);
	}
	
	public static String KnowWhoReply(Matchers m) {
		String player = m.get("player");
		
		if (player == null) return getResponse("WhatAreYouTalkingAbout");
		
		String type = WordList.getTypeByWord(player);
		logln("[ResponseProcess] KnowWhoReply - Type " + type);
		
		if (type == null) return getResponse("DontKnowWho", m);
		if (type.equals("Penut")) return getResponse("SomeoneIsHandsome", m);
		if (type.equals("Alex")) return getResponse("KnowAlex", m);
		if (type.equals("Leon")) return getResponse("KnowLeon", m);
		
		return getResponse("DontKnowWho", m);
	}
	
	public static String IsGameHasDeal(Matchers m) {
		String game = m.get("game");
		
		if (game == null) return getResponse("WhatAreYouTalkingAbout");
		
		String[] deal_info = SteamDeal.isGameInDeal(game).split(";");
		
		m.add("deal", deal_info[0]);
		m.add("game_title", deal_info[1]);
		m.add("game_link", deal_info[2]);
		
		return getResponse("GameHasDeal", m);
	}
	
	public static String DemoA(Matchers m) {
		m.add("game", "星海爭霸");
		return IsGameHasNews(m);
	}
	
	public static String DemoB(Matchers m) {
		m.add("game", "ASTRONEER");
		return IsGameHasDeal(m);
	}
	
	// Testing Function
	
	public static void main(String[] args) throws Exception {
		unitTest2();
	}
	
	public static void unitTest1() throws Exception {
		Class<?>[] param = {Matchers.class};
		Matchers m = new Matchers();
		m.add("game", "真三國無雙 8");
		System.out.println(ResponseProcess.class.getMethod("IsGameHasNews", param).invoke(null, m));
	}
	
	public static void unitTest2() {
		Matchers m = new Matchers();
		m.add("game", "null");
		System.out.println(ResponseProcess.doResponseProcess("DemoB", m));
	}
}
