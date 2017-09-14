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
		int[][] result = new int[mPair.size() + 1][sentence.length() + 1];
		
		for (int i = 0; i < mPair.size(); i++) {
			for (int j = 0; j < sentence.length(); j++) {
				if (mPair.get(i).isInclude(sentence.substring(j, j + 1)))
					result[i + 1][j + 1] = 1;
				else result[i + 1][j + 1] = 0;
			}
		}
		
		// Count Table
		for (int i = 1; i <= mPair.size(); i++) {
			for (int j = 1; j <= sentence.length(); j++) {
				int t = 0;
				if (result[i][j] == 1) t = result[i - 1][j - 1] + 1;
				if (result[i][j - 1] > result[i - 1][j])
					result[i][j] = result[i][j - 1];
				else result[i][j] = result[i - 1][j];
				if (t > result[i][j]) result[i][j] = t;
			}
		}
		
		System.out.println(toString());
		System.out.println(sentence);
		for (int[] b: result) {
			for (int bb: b)
				System.out.print(bb + " ");
			System.out.print("\n");
		}
		
		PatternMatch pm = new PatternMatch(mPatternType);
		
		return pm;
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
				word = LibraryIO.readFile(NerdPattern.DIR_PATH + word[0]).split(" ");
			
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
		
		public boolean isInclude(String s) {
			for (String ss: mWord)
				if (ss.contains(s)) return true;
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
