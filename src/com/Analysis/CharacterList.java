package com.Analysis;

import java.util.*;

import com.Library.LibraryIO;

public class CharacterList {
	String DATA_PATH;
	HashMap<String, Boolean> mListMap = new HashMap<>();
	
	static CharacterList AlphabetList = new CharacterList("data\\list_alphabet.txt");
	static CharacterList SymbolList = new CharacterList("data\\list_symbol.txt");
	
	public CharacterList(String path) {
		DATA_PATH = path;
		mListMap = loadData();
	}
	
	public HashMap<String, Boolean> loadData() {
		HashMap<String, Boolean> map = new HashMap<>();
		
		String[] content = LibraryIO.readFileLines(DATA_PATH);
		if (content == null) return null;
		
		for (String s: content)
			for (int i = 0; i < s.length() - 1; i++)
				map.put(s.substring(i, i + 1), true);
		
		return map;
	}
	
	public boolean isInList(String s) {
		for (int i = 0; i < s.length(); i++)
			if (mListMap.get(s.substring(i, i + 1)) == null)
				return false;
		return true;
	}
	
	public String matchList(String s) {
		int i;
		for (i = 0; i < s.length(); i++)
			if (!isInList(s.substring(i, i + 1)))
				break;

		return s.substring(0, i);
	}
	
	public static String matchAlphabet(String s) {
		return AlphabetList.matchList(s);
	}
	
	public static String matchEmoji(String s) {
		return SymbolList.matchList(s);
	}
}
