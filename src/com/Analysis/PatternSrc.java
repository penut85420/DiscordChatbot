package com.Analysis;

import java.util.*;

import com.Library.LibraryIO;

public class PatternSrc {
	String mPatternType;
	ArrayList<Pair> mPair;
	
	public PatternSrc(String type) {
		mPatternType = type;
		mPair = new ArrayList<>();
	}
	
	public void addPair(String[] pair, String tag) {
		mPair.add(new Pair(pair, tag));
	}
	
	public PatternMatch match(String sentence) {
		String[] word = sentence.split(" ");
		int[][] result = new int[mPair.size()][word.length];
		
		for (int i = 0; i < word.length; i++) {
			for (int j = 0; j < mPair.size(); j++) {
				if (mPair.get(j).isMatch(word[i]))
					result[j][i] = 1;
			}
		}
		
		log(mPatternType + "\n");
		log(Arrays.toString(word) + "\n");
		for (int[] i: result) {
			for (int j: i) {
				log(j + " ");
			}
			log("\n");
		}
		return null;
	}
	
	private void log(Object obj) {
		System.out.print(obj.toString());
	}
	
	public String toString() {
		String s = mPatternType + " {\n";
		for (Pair p: mPair)
			s += "  " + p.toString() + "\n";
		s += "}";
		return s;
	}
	
	class Pair {
		ArrayList<String> mWord;
		String mTag;
		
		public Pair(String[] word, String tag) {
			mTag = tag;
			
			if (tag.equals("&"))
				word = LibraryIO.readFile(NerdPattern.DIR_PATH + word[0]).split("[ \r\n]");
			
			mWord = new ArrayList<String>();
			for (String s: word)
				mWord.add(s);
		}
		
		public ArrayList<String> getWordList() {
			return mWord;
		}
		
		public boolean isMatch(String s) {
			for (String ss: mWord)
				if (ss.equals(s)) return true;

			return false;
		}
		
		public String toString() {
			String s = mTag + ":";
			
			for (String ss: mWord)
				s += " " + ss;
			
			return s;
		}
	}
}
