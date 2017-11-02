package com.Analysis;

import java.util.ArrayList;

import com.Library.LibraryMath;

public class Pair {
	ArrayList<String> mWord = new ArrayList<>();
	String mTag;
	String mTagName;
	
	static final String TagEnum = "#";
	static final String TagList = "&";
	static final String TagIgnorable = "|";
	static final String TagUnknown = "@";
	static final String TagBreakLine = "\\";
	
	public Pair(String tag, String word) {
		mTag = tag;
		mTagName = word;
		
		for (String s: word.split(","))
			mWord.add(s);
	}

	public String getTag() { return mTag; }
	
	public String getTagName() { return mTagName; }
	
	public boolean isTagList() { return mTag.equals(TagList); }
	public boolean isTagUnknown() { return mTag.equals(TagUnknown); }
	public boolean isTagBreakLine() { return mTag.equals(TagBreakLine); }
	
	public ArrayList<String> getWordList() {
		if (isTagList()) return WordList.getWordList(mWord.get(0));
		return mWord;
	}
	
	public String getRndPair() {
		if (isTagBreakLine()) return "\n";
		if (isTagUnknown()) return null;
		ArrayList<String> arr = getWordList();
		return arr.get(LibraryMath.getRandNum(arr.size()));
	}

	public boolean isMatch(String s) {
		for (String ss : getWordList())
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