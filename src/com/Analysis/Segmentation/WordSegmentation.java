package com.Analysis.Segmentation;

import static com.Library.LibraryUtil.log;

public class WordSegmentation {
	static final String MainDictionaryPath = "testing\\dict.txt";
	static final String UserDictionaryPath = "testing\\user.txt";
	
	static Dictionary mDictionary;
	static Boolean isInit = false;
	
	public static void main(String[] args) {
		log(WordSegmentation.MaximumMatch("我覺得星爆氣流好難"));
	}
	
	public static void init() {
		isInit = true;
		mDictionary = new Dictionary(MainDictionaryPath, UserDictionaryPath);
		System.out.println(mDictionary);
	}
	
	public static String MaximumMatch(String s) {
		if (!isInit) init();
		
		String result = "";
		
		for (int i = 0; i < s.length(); ) {
			String match = mDictionary.match(s.substring(i));
			System.out.println("Match: " + match);
			i += match.length();
			result += match + " ";
		}
		
		return result;
	}
}
