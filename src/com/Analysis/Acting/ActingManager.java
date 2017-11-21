package com.Analysis.Acting;

import java.util.*;

import com.Library.*;

import static com.Library.LibraryUtil.log;

public class ActingManager {
	static final String DATA_PATH = "data\\pattern\\Acting\\ActingPattern.dat";

	static ArrayList<ActingSrc> mPatternList = initPatternList();
	
	public static ArrayList<ActingSrc> initPatternList() {
		ArrayList<ActingSrc> list = new ArrayList<>();
		String[] pattern = LibraryIO.readFileLines(DATA_PATH);
		String type = "";
		
		for (String line: pattern) {
			// 前置Comment處理
			line = LibraryUtil.removeComment(line);
			if (line.isEmpty()) continue;
			
			// 設定Type
			if (line.startsWith("-")) {
				type = line.substring(1);
				continue;
			}
			
			// 讀取Pattern
			String[] term = line.split(" ");
			ActingSrc as = new ActingSrc(type);
			for (String token: term)
				as.addPair(token);
			list.add(as);
		}
		
		return list;
	}
	
	public static ActingMatch match(String sentence) {
		for (ActingSrc ps: mPatternList) {
			ActingMatch m = ps.match(sentence);
			if (m != null) return m;
		}
		return null;
	}
	
	public static void main(String[] args) {
		unitTest();
		unitTest2("我 貓咪大戰爭 都 打 不過");
		unitTest2("我 Nation Blue 各種 full 不掉");
	}
	
	public static void unitTest() {
		for (ActingSrc as: mPatternList)
			log(as + "\n\n");
	}
	
	public static void unitTest2(String s) {
		ActingMatch am = match(s);
		log(am + "\n");
	}
}
