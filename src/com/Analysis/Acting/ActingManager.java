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
		// unitTest();
		// match("嗨 肥宅");
		// ActingMatch am = ActingManager.match("我 覺得 桐人 星爆 星爆氣流斬 好 難 喔 www");
		ActingMatch am = match("我 覺得 星爆氣流斬 好 難");
		System.out.println(am);
	}
	
	public static void unitTest() {
		for (ActingSrc ps: mPatternList)
			log(ps + "\n\n");
	}
}
