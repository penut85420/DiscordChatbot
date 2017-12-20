package com.Analysis;

import java.util.ArrayList;

import com.Library.LibraryMath;

public class Pair {
	ArrayList<String> mWord = new ArrayList<>();
	String mTag;
	String mTagName;
	
	static final String TagEnum = "#";
	static final String TagList = "&";
	static final String TagOption = "|";
	static final String TagSlot = "@";
	static final String TagBreakLine = "\\";
	
	public Pair(String tag, String word) {
		mTag = tag;
		mTagName = word;
		
		for (String s: word.split(",")) {
			if (mTag.equals(TagEnum))
				s = s.replace("_", " ");
			mWord.add(s);
		}
	}

	public String getTag() { return mTag; }
	public String getTagName() { return mTagName; }
	
	public boolean isTagList() { return mTag.equals(TagList); }
	public boolean isTagSlot() { return mTag.equals(TagSlot); }
	public boolean isTagBreakLine() { return mTag.equals(TagBreakLine); }
	
	public ArrayList<String> getWordList() {
		if (isTagList()) return WordList.getWordList(mWord.get(0));
		return mWord;
	}
	
	public String getRndPair() {
		if (isTagBreakLine()) return "\n";
		if (isTagSlot()) return null;
		ArrayList<String> arr = getWordList();
		return arr.get(LibraryMath.getRandNum(arr.size()));
	}

	public boolean isMatch(String s) {
		ArrayList<String> list = getWordList();
		if (getWordList() == null) return false;
		
		for (String ss : list)
			if (ss.equals(s))
				return true;
		return false;
	}

	public String toString() {
		String s = getTag() + ":";

		for (String ss : getWordList())
			s += " " + ss;

		return s;
	}
}