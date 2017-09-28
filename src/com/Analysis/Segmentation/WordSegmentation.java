package com.Analysis.Segmentation;

import static com.Library.LibraryUtil.log;

import com.Library.LibraryIO;

public class WordSegmentation {
	static final String MainDictionaryPath = "testing\\dict.txt";
	static final String UserDictionaryPath = "testing\\user.txt";
	static final String Sample = "sample\\chat\\ChatSample_0002.txt";
	
	static Dictionary mDictionary;
	static Boolean isInit = false;
	
	public static void main(String[] args) {
//		String[] sample = LibraryIO.readFileLines(Sample);
//		for (String s: sample)
//			log(WordSegmentation.MaximumMatch(s));
		log(WordSegmentation.MaximumMatch("我覺得星爆氣流斬好難"));
		log(WordSegmentation.MaximumMatch("這個折扣真難得"));
		log(WordSegmentation.MaximumMatch("...OAO真的嗎0.0?"));
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
			// System.out.println("Match: " + match);
			i += match.length();
			result += match + "　";
		}
		
		return result;
	}
}
