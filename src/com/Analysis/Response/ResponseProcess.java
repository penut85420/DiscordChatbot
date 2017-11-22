package com.Analysis.Response;

import com.Analysis.Matchers;

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
		m.add("news_title", "[問卦] 有沒有貓咪大戰爭的八卦?");
		m.add("news_link", "http://www.abc.com");
		return ResponseManager.getResponse("GameNoNews", 0, m);
	}
	
	public static void main(String[] args) throws Exception {
		// IsGameHasNews
		Class<?>[] param = {Matchers.class};
		System.out.println(ResponseProcess.class.getMethod("IsGameHasNews", param).invoke(null, new Matchers()));
	}
}
