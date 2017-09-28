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
		int[][] result = new int[mPair.size() + 1][word.length + 1];
		
		for (int i = 0; i < mPair.size() + 1; i++) {
			result[i][0] = 0;
		}
		
		for (int i = 0; i < word.length + 1; i++) {
			result[0][i] = 0;
		}
		
		boolean matched = false;
		for (int i = 1; i < mPair.size() + 1; i++) {
			for (int j = 1; j < word.length + 1; j++) {
				matched = mPair.get(i - 1).isMatch(word[j - 1]);
				result[i][j] = result[i-1][j];
				if (result[i][j] < result[i][j-1])
					result[i][j] = result[i][j-1];
				
				if (matched && result[i][j] < result[i-1][j-1] + 1) {
					result[i][j] = result[i - 1][j - 1] + 1;
				}
//				if (result[i - 1][j - 1] + 1 > result[i - 1][j] && result[i - 1][j - 1] + 1 > result[i][j - 1]
//						&& matched)
//					result[i][j] = result[i - 1][j - 1] + 1;
//				else if (result[i - 1][j] == result[i][j - 1])
//					result[i][j] = result[i - 1][j];
//				else if (result[i - 1][j] > result[i][j - 1])
//					result[i][j] = result[i - 1][j];
//				else
//					result[i][j] = result[i][j - 1];
			}
		}
		
		log(mPatternType + "\n");
		log(Arrays.toString(word) + "\n");
		for (int[] i : result) {
			for (int j : i) {
				log(j + " ");
			}
			log("\n");
		}
		
		if (result[mPair.size()][word.length] == mPair.size())
			log("MATCH!!\n");
		return null;
	}

	private void log(Object obj) {
		System.out.print(obj.toString());
	}

	public String toString() {
		String s = mPatternType + " {\n";
		for (Pair p : mPair)
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
			for (String s : word)
				mWord.add(s);
		}

		public ArrayList<String> getWordList() {
			return mWord;
		}

		public boolean isMatch(String s) {
			for (String ss : mWord)
				if (ss.equals(s))
					return true;
			return false;
		}

		public String toString() {
			String s = mTag + ":";

			for (String ss : mWord)
				s += " " + ss;

			return s;
		}
	}
}
