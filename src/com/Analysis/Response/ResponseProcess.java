package com.Analysis.Response;

import com.Analysis.Matchers;
import com.Analysis.WordList;
import com.InternetResource.GameNewsGNN;
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
		String news = GameNewsGNN.getNewsInfo(m.get("game"));
		
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
		
		return getResponse("DontKnowWho", m);
	}
	
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
		m.add("player", "土豆");
		System.out.println(ResponseProcess.doResponseProcess("KnowWhoReply", m));
	}
}
