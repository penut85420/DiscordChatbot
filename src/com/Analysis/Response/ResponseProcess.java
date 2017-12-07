package com.Analysis.Response;

import com.Analysis.Matchers;
import com.Analysis.WordList;
import com.InternetResource.GameNewsGNN;

public class ResponseProcess {
	
	public static String getResponse(String type, Matchers m) {
		System.out.println("[ResponseMethod] getResponse - Type: " + type);
		try {
			Class<?>[] param = {Matchers.class};
			return (String) ResponseProcess.class.getMethod(type, param).invoke(null, m);
		} catch (Exception e) {
			System.out.println("[ResponseMethod] Error Occur");
			e.printStackTrace(System.out);
		}
		return null;
	}
	
	public static String IsGameHasNews(Matchers m) {
		String news = GameNewsGNN.getNewsInfo(m.get("game"));
		
		if (news == null) return ResponseManager.getResponse("GameNoNews", 0, m); 
		
		String[] info = news.split(";");
		
		m.add("news_title", info[0]);
		m.add("news_link", info[1]);
		
		return ResponseManager.getResponse("GameHasNews", 0, m);
	}
	
	public static String KnowWhoReply(Matchers m) {
		String player = m.get("player");
		
		if (player == null) return "你在說啥?";
		
		String type = WordList.getTypeByWord(player);

		if (type.equals("Penut")) return "認識啊，他很帥ω";
		
		return "我不認識" + player + "欸";
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
		m.add("player", "Penut");
		System.out.println(ResponseProcess.getResponse("KnowWhoReply", m));
	}
}
