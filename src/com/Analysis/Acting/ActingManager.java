package com.Analysis.Acting;

import java.util.*;

import com.Library.LibraryIO;
import static com.Library.LibraryUtil.log;

public class ActingManager {
	public static String DIR_PATH = "data/pattern/";
	private static String FILE_PATTERN = "pattern.dat";
	
	ArrayList<ActingSrc> mPatternList = new ArrayList<>();

	public static void main(String[] args) {
		ActingManager np = new ActingManager();
		np.match("嗨 肥宅");
		np.match("我 覺得 星爆 星 星 星 氣流斬 好 難 喔 www");
	}
	
	public ActingManager() {
		String[] pattern = LibraryIO.readFileLines(DIR_PATH + FILE_PATTERN);
		
		for (String p: pattern) {
			String[] term = p.split(" ");
			ActingSrc ps = new ActingSrc(term[0].substring(1));
			for (int i = 1; i < term.length; i++) {
				String tag = term[i].substring(0, 1);
				term[i] = term[i].substring(1);
				ps.addPair(tag, term[i]);
			}
			mPatternList.add(ps);
		}
	}
	
	public void match(String sentence) {
		for (ActingSrc ps: mPatternList) {
			ps.match(sentence);
		}
	}
	
	public void unitTest() {
		for (ActingSrc ps: mPatternList)
			log(ps);
	}
}
