package com.Analysis.Segmentation;

import static com.Library.LibraryUtil.log;

public class WordSegmentation {
	static final String MainDictionaryPath = "testing\\dict_sort.txt";
	static final String UserDictionaryPath = "testing\\user.txt";
	
	static Dictionary mDictionary;
	static Boolean isInit = false;
	
	public static void main(String[] args) {
		log(WordSegmentation.MaximumMatch("我覺得星爆氣流斬好難"));
		log(WordSegmentation.MaximumMatch("這個折扣真難得"));
		log(WordSegmentation.MaximumMatch("...OAO真的嗎0.0?"));
	}
	
	public static void init() {
		isInit = true;
		mDictionary = new Dictionary(MainDictionaryPath, UserDictionaryPath);
	}
	
	public static String MaximumMatch(String s) {
		if (!isInit) init();
		
		String result = "";
		
		for (int i = 0; i < s.length(); ) {
			String match = mDictionary.match(s.substring(i));
			// System.out.println("Match: " + match);
			i += match.length();
			result += match + " ";
		}
		
		return result;
	}
}
