package com.Analysis;

import java.util.*;

import com.Library.*;

public class WordList {
	static final String WordListPath = "data\\Pattern\\WordList.txt";
	
	static HashMap<String, ArrayList<Word>> mWordList = init();
	
	// Initialize Data
	private static HashMap<String, ArrayList<Word>> init() {
		HashMap<String, ArrayList<Word>> wordList = new HashMap<>();
		
		String nowWordType = "";
		String[] lines = LibraryIO.readFileLines(WordListPath);
		
		for (String line: lines) {
			if (line.startsWith("-")) {
				nowWordType = line.substring(1);
				if (wordList.get(nowWordType) == null)
					wordList.put(nowWordType, new ArrayList<>());
				continue;
			}
			if (line.isEmpty()) continue;
			
			wordList.get(nowWordType).add(new Word(line));
		}
		
		return wordList;
	}
	
	// Get a random word from the list
	public static String getRandomWord(String type) {
		ArrayList<Word> list = mWordList.get(type);
		
		if (list == null) return null;
		
		return list.get(LibraryMath.getRandNum(list.size())).getWord();
	}
	
	// Get a random word from the list with emotion
	public static String getRandomWord(String type, int emotion) {
		ArrayList<Word> list = mWordList.get(type),
				tmpList = new ArrayList<>();
		
		if (list == null) return null;
		
		for (Word w: list)
			if (w.isInRange(emotion))
				tmpList.add(w);
		
		if (tmpList.size() == 0) return null;
		
		return tmpList.get(LibraryMath.getRandNum(tmpList.size())).getWord();
	}
	
	// Get a word list of a type
	public static ArrayList<String> getWordList(String type) {
		ArrayList<Word> word = mWordList.get(type);
		if (word == null) return null;
		
		ArrayList<String> list = new ArrayList<>();
		for (Word w: word)
			list.add(w.getWord());
		return list;
	}
	
	// Get type by word
	public static String getTypeByWord(String word) {
		for (String type: mWordList.keySet())
			for (Word w: mWordList.get(type))
				if (w.getWord().equals(word)) return type;
		return null;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++)
		LibraryUtil.log(getRandomWord("HappyEnd", 5) + "\n");
	}
	
	static class Word {
		String mWord;
		int mLowBound = 0;
		int mHighBound = 9;
		
		public Word(String s) {
			String[] seg = s.split(" ");
			if (seg.length > 1) {
				int[] bound = LibraryUtil.getBound(seg[0]);
				mLowBound = bound[0];
				mHighBound = bound[1];
			}
			mWord = seg[seg.length - 1];
		}
		
		public String getWord() { return mWord; }
		
		public boolean isInRange(int n) {
			if (n < mLowBound) return false;
			if (n > mHighBound) return false;
			return true;
		}
	}
}
