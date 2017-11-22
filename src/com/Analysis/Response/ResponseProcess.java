package com.Analysis.Response;

import com.Analysis.Matchers;
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
	
	public static void main(String[] args) throws Exception {
		Class<?>[] param = {Matchers.class};
		Matchers m = new Matchers();
		m.add("game", "真三國無雙 8");
		System.out.println(ResponseProcess.class.getMethod("IsGameHasNews", param).invoke(null, m));
	}
}
