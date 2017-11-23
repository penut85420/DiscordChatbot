package com.InternetResource;

import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.Library.LibraryIO;
import com.Library.LibraryUtil;

import java.io.*;

public class GameNewsGNN {
	public static ArrayList<NewsTitle> mNewsTitleList = init();
	
	public static void main(String[] args) {
		LibraryUtil.logArr(mNewsTitleList.toArray());
		System.out.println(getNewsInfo("J Team"));
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
			
			// Backup
			LibraryIO.writeFile("testing\\GNN.xml", content);
			
			// Retrive Title And Link
			System.out.println("[GNN] Init Success");
			return getTitle(content);
		} catch (Exception e) {
			System.err.println("[GNN] Err in FindGNNNews init()");
			e.printStackTrace();
		}
		
		return null;
	}

	public static ArrayList<NewsTitle> getTitle(String content) {
		ArrayList<NewsTitle> titleList = new ArrayList<>();
		String preTitleTag = "<title><![CDATA[", 
				endTitleTag = "]]></title>",
				preThemeTag = "《",
				endThemeTag = "》",
				preLinkTag = "<link><![CDATA[",
				endLinkTag = "]]></link>";

		while (content.indexOf(preTitleTag) > 0) {
			String title = retriveByTag(content, preTitleTag, endTitleTag);
			String theme = retriveByTag(content, preThemeTag, endThemeTag);
			String link = retriveByTag(content, preLinkTag, endLinkTag);
			
			titleList.add(new NewsTitle(title, theme, link));
			
			content = content.substring(content.indexOf(endLinkTag) + endLinkTag.length());
		}
		
		return titleList;
	}
	
	private static String retriveByTag(String str, String preTag, String endTag) {
		int preIdx = str.indexOf(preTag),
			endIdx = str.indexOf(endTag);
		
		if (preIdx < 0) return null;
		preIdx += preTag.length();
		if (preIdx > endIdx) return null;
		
		return str.substring(str.indexOf(preTag) + preTag.length(), str.indexOf(endTag));
	}
	
	public static String getNewsInfo(String game) {
		if (game != null)
		for (NewsTitle nt: mNewsTitleList)
			if (nt.isThemeMatch(game)) return nt.getTitle() + ";" + nt.getLink();
		
		return null;
	}
	
	static class NewsTitle {
		String mCompleteTitle;
		String mMainTheme;
		String mLink;
		
		public NewsTitle(String title, String theme, String link) {
			mCompleteTitle = title;
			mMainTheme = theme;
			mLink = link;
		}
		
		public String getTitle() { return mCompleteTitle; }
		public String getLink() { return mLink; }
		
		public boolean isThemeExist() { return mMainTheme != null; }
		
		public boolean isThemeMatch(String theme) {
			// TODO 部分Match
			// TODO Theme不存在
			if (isThemeExist() && mMainTheme.indexOf(theme) >= 0) return true;
			if (mCompleteTitle.indexOf(theme) >= 0) return true;
			return false;
		}
		
		public String toString() {
			return mCompleteTitle + "\n<" + mMainTheme + "> " + mLink + "\n";
		}
	}
}
