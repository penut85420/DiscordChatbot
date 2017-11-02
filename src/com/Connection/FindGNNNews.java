package com.Connection;

import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class FindGNNNews {
	public static ArrayList<NewsTitle> mNewsTitleList = init();
	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(mNewsTitleList.toArray()).replaceAll(", ", "\n"));
		System.out.println(matchTitle("英雄聯盟"));
	}

	public static ArrayList<NewsTitle> init() {
		String content = "";
		
		try {
			// Create Connection
			URLConnection gnnCon = new URL("https://gnn.gamer.com.tw/rss.xml").openConnection();
			// Set Connection Header
			gnnCon.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) "
					+ "AppleWebKit/537.11 (KHTML, like Gecko) "
					+ "Chrome/23.0.1271.95 Safari/537.11");
			// Start Connecting
			gnnCon.connect();

			// Create Input Stream
			BufferedReader in = new BufferedReader(new InputStreamReader(gnnCon.getInputStream(), Charset.forName("BIG5")));

			// Read Content
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				content += inputLine + "\n";
			in.close();
			
			// Retrive Title
			return getTitle(content);
		} catch (Exception e) {
			System.err.println("Err in FindGNNNews init()");
			e.printStackTrace();
		}
		
		System.out.println("Done");
		return null;
	}

	public static ArrayList<NewsTitle> getTitle(String content) {
		ArrayList<NewsTitle> titleList = new ArrayList<>();
		String preTitleTag = "<title><![CDATA[", 
				endTitleTag = "]]></title>",
				preThemeTag = "《",
				endThemeTag = "》";

		while (content.indexOf(preTitleTag) > 0) {
			String title = content.substring(content.indexOf(preTitleTag) + preTitleTag.length(), content.indexOf(endTitleTag));
			String theme = null;
			if (title.indexOf(preThemeTag) < title.indexOf(endThemeTag)) 
				theme = title.substring(title.indexOf(preThemeTag) + preThemeTag.length(), title.indexOf(endThemeTag));
			titleList.add(new NewsTitle(title, theme));
			content = content.substring(content.indexOf(endTitleTag) + endTitleTag.length());
		}
		
		return titleList;
	}
	
	public static String matchTitle(String game) {
		for (NewsTitle nt: mNewsTitleList)
			if (nt.isThemeMatch(game)) return nt.getTitle();
		
		return null;
	}
	
	public static class NewsTitle {
		String mCompleteTitle;
		String mMainTheme;
		
		public NewsTitle(String title, String theme) {
			mCompleteTitle = title;
			mMainTheme = theme;
		}
		
		public String getTitle() { return mCompleteTitle; }
		
		public boolean isThemeExist() { return mMainTheme != null; }
		
		public boolean isThemeMatch(String theme) {
			// TODO 部分Match
			// TODO Theme不存在
			if (isThemeExist() && theme.equals(mMainTheme)) return true;
			return false;
		}
		
		public String toString() {
			return mCompleteTitle + "\t" + mMainTheme;
		}
	}
}
