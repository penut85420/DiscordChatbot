package com.Analysis;

import java.util.*;

import com.Library.LibraryIO;
import static com.Library.LibraryUtil.log;

public class NerdPattern {
	public static String DIR_PATH = "data/pattern/";
	private static String FILE_PATTERN = "pattern.dat";
	
	ArrayList<PatternSrc> mPatternList;

	public static void main(String[] args) {
		NerdPattern np = new NerdPattern();
		np.match("嗨 肥宅");
		np.match("我 覺得 星爆 星 星 星 氣流斬 好 難 喔 www");
	}
	
	public NerdPattern() {
		mPatternList = new ArrayList<>();
		
		String[] pattern = LibraryIO.readFile(DIR_PATH + FILE_PATTERN)
				.replaceAll("\r", "").split("\n");
		
		for (String p: pattern) {
			String[] term = p.split(" ");
			PatternSrc ps = new PatternSrc(term[0].substring(1));
			for (int i = 1; i < term.length; i++) {
				String tag = term[i].substring(0, 1);
				term[i] = term[i].substring(1);
				ps.addPair(term[i].split(","), tag);
			}
			mPatternList.add(ps);
		}
	}
	
	public void match(String sentence) {
		for (PatternSrc ps: mPatternList) {
			ps.match(sentence);
		}
	}
	
	public void unitTest() {
		for (PatternSrc ps: mPatternList)
			log(ps);
	}
}
