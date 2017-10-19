package com.Analysis;

import java.util.ArrayList;

import com.Analysis.Pattern.NerdPattern;
import com.Library.LibraryIO;

public class WordPair {
		ArrayList<String> mWord;
		private String mTag;

		public WordPair(String[] word, String tag) {
			setTag(tag);

			if (tag.equals("&"))
				word = LibraryIO.readFile(NerdPattern.DIR_PATH + word[0]).split("[ \r\n]");

			mWord = new ArrayList<String>();
			for (String s : word)
				mWord.add(s);
		}

		public String getTag() {
			return mTag;
		}

		public void setTag(String mTag) {
			this.mTag = mTag;
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
			String s = getTag() + ":";

			for (String ss : mWord)
				s += " " + ss;

			return s;
		}
	}