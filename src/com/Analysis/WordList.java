package com.Analysis;

import java.util.*;

import com.Library.*;

public class WordList {
	static final String WordListPath = "data\\pattern\\WordList.txt";
	
	static HashMap<String, ArrayList<String>> mWordList = init();
	
	private static HashMap<String, ArrayList<String>> init() {
		HashMap<String, ArrayList<String>> wordList = new HashMap<>();
		
		String nowWordType = "";
		String[] lines = LibraryIO.readFileLines(WordListPath);
		
		for (String line: lines) {
			if (line.startsWith("-")) {
				nowWordType = line.substring(1);
				if (wordList.get(nowWordType) == null)
					wordList.put(nowWordType, new ArrayList<>());
				continue;
			}
			wordList.get(nowWordType).add(line);
		}
		
		return wordList;
	}
	
	public static String getRandomWord(String type) {
		ArrayList<String> list = mWordList.get(type);
		
		if (list == null) return null;
		
		return list.get(LibraryMath.getRandNum(list.size()));
	}
	
	public static void main(String[] args) {
		LibraryUtil.log(getRandomWord("what"));
	}
}
