package com.Analysis.Segmentation;

import static com.Library.LibraryUtil.log;

import java.io.*;

import com.Database.DataBaseManager;
import com.Library.LibraryIO;

public class WordSegmentation {
	static final String MainDictionaryPath = "data\\dictionary\\dictionary_main.txt";
	static final String UserDictionaryPath = "data\\dictionary\\dictionary_user_sort.txt";
	static final String TinyDictionaryPath = "data\\dictionary\\dictionary_small.txt";
	
	static Dictionary mDictionary = init();
	// static Dictionary mDictionary = new Dictionary(TinyDictionaryPath, UserDictionaryPath);
	
	private static Dictionary init() {
		Dictionary dict = new Dictionary(MainDictionaryPath, UserDictionaryPath);
		for (String s: DataBaseManager.getUserList())
			dict.add(s);
		return dict;
	}
	
	public static String MaximumMatch(String s) {
		String result = "";
		
		for (int i = 0; i < s.length(); ) {
			String match = mDictionary.match(s.substring(i));
			// System.out.println("Match: " + match);
			i += match.length();
			result += match + " ";
		}
		
		return result.trim();
	}
	
	public static void addNewWord(String s) { mDictionary.add(s); }
	
	public static void main(String[] args) throws Exception {
		// unitTest1();
		// unitTest2();
		// unitTest3();
		log(MaximumMatch("Penut"));
	}
	
	public static void unitTest1() throws Exception {
		File folder = new File("testing\\plaintxt\\");
		String folder_out = "testing\\wSeg\\";
		for (File f: folder.listFiles()) {
			String[] content = LibraryIO.readFileLines(f.getAbsolutePath());
			FileOutputStream fout = new FileOutputStream(folder_out + f.getName());
			fout.write(new String("\uFEFF").getBytes("UTF-8"));
			for (String s: content)
				fout.write(WordSegmentation.MaximumMatch(s).getBytes("UTF-8"));
			fout.close();
		}
	}
	
	public static void unitTest2() {
//		log(WordSegmentation.MaximumMatch("我覺得星爆氣流斬好難"));
//		log(WordSegmentation.MaximumMatch("這個折扣真難得"));
//		log(WordSegmentation.MaximumMatch("...OAO真的嗎0.0?"));
//		log(WordSegmentation.MaximumMatch("變形金剛真好看"));
//		log(WordSegmentation.MaximumMatch("我是一隻小小小鳥，高高高高飛在天上"));
		log(WordSegmentation.MaximumMatch("薯條喵喵叫OuO"));
	}
	
	public static void unitTest3() {
		String[] fin = LibraryIO.readFileLines("data\\pattern\\Acting\\ActingPattern.dat");
		String fout = "";
		for (String s: fin)
			fout += MaximumMatch(s) + "\r\n";
		LibraryIO.writeFile("testing\\acting_pattern_wSeg.txt", fout);
	}
}
