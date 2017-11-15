package com.Analysis.Acting;

import java.util.*;

import com.Library.*;

import static com.Library.LibraryUtil.log;

public class ActingManager {
	static final String DATA_PATH = "data\\pattern\\pattern.dat";

	static ArrayList<ActingSrc> mPatternList = initPatternList();

	public static void main(String[] args) {
		// unitTest();
		// match("嗨 肥宅");
		// ActingMatch am = ActingManager.match("我 覺得 桐人 星爆 星爆氣流斬 好 難 喔 www");
		ActingMatch am = match("我 覺得 星爆氣流斬 好 難");
		System.out.println(am);
	}
	
	public static ArrayList<ActingSrc> initPatternList() {
		ArrayList<ActingSrc> list = new ArrayList<>();
		String[] pattern = LibraryIO.readFileLines(DATA_PATH);
		
		for (String p: pattern) {
			String[] term = p.split(" ");
			ActingSrc ps = new ActingSrc(term[0].substring(1));
			for (int i = 1; i < term.length; i++) {
				String tag = term[i].substring(0, 1);
				term[i] = term[i].substring(1);
				ps.addPair(tag, term[i]);
			}
			list.add(ps);
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
	
	public static void unitTest() {
		for (ActingSrc ps: mPatternList)
			log(ps + "\n\n");
	}
}
