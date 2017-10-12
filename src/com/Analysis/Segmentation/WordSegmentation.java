package com.Analysis.Segmentation;

import static com.Library.LibraryUtil.log;

import java.io.*;

import com.Library.LibraryIO;

public class WordSegmentation {
	static final String MainDictionaryPath = "data\\dictionary\\dictionary_main.txt";
	static final String UserDictionaryPath = "data\\dictionary\\dictionary_user.txt";
	
	static Dictionary mDictionary = new Dictionary(MainDictionaryPath, UserDictionaryPath);;
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
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
		
		log(WordSegmentation.MaximumMatch("我覺得星爆氣流斬好難"));
		log(WordSegmentation.MaximumMatch("這個折扣真難得"));
		log(WordSegmentation.MaximumMatch("...OAO真的嗎0.0?"));
		log(WordSegmentation.MaximumMatch("變形金剛真好看"));
		log(WordSegmentation.MaximumMatch("我是一隻小小小鳥，高高高高飛在天上"));
	}
	
	public static String MaximumMatch(String s) {
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
