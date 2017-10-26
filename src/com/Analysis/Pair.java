package com.Analysis;

import java.util.ArrayList;

import com.Analysis.Acting.ActingManager;
import com.Library.LibraryIO;

public class Pair {
	ArrayList<String> mWord = new ArrayList<>();
	private String mTag;
	
	static final String TagEnum = "#";
	static final String TagList = "&";
	static final String TagIgnorable = "|";
	static final String TagUnknown = "@";

	@Deprecated
	public Pair(String[] word, String tag) {
		setTag(tag);

		if (tag.equals(TagList))
			word = LibraryIO.readFile(ActingManager.DIR_PATH + word[0]).split("[ \r\n]");

		for (String s : word)
			mWord.add(s);
	}
	
	public Pair(String tag, String word) {
		setTag(tag);
		
		for (String s: word.split(","))
			mWord.add(s);
	}

	public String getTag() { return mTag; }

	public void setTag(String mTag) { this.mTag = mTag; }

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
		String s = getTag() + ":";

		for (String ss : mWord)
			s += " " + ss;

		return s;
	}
}