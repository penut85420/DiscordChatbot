package com.Analysis.Segmentation;

import java.util.*;

import com.Library.LibraryIO;
import static com.Library.LibraryUtil.log;

public class Dictionary {
	static HashMap<String, Node> mDictionaryMap = new HashMap<>();
	
	public Dictionary(String dictPath, String userDict) {
		add(LibraryIO.readFileLines(dictPath));
		add(LibraryIO.readFileLines(userDict));
	}
	
	public void add(String[] words) {
		if (words == null) return ;
		
		for (String token: words) {
			if (token.length() == 1) {
				mDictionaryMap.put(token, new Node(token));
				continue;
			}
			
			String first = token.substring(0, 1);
			mDictionaryMap.get(first).add(token.substring(1));
		}
	}
	
	public String match(String s) {
		String first = s.substring(0, 1);
		Node n = mDictionaryMap.get(first);
		if (n == null) return first;
		String result = n.match(s, first, first);

		return result;
	}
	
	public String toString() {
		String s = "";
		for (String key: mDictionaryMap.keySet()) {
			s += key + ": \n";
			s += mDictionaryMap.get(key).toString();
		}
			
		return s;
	}
	
	public static void main(String[] args) {
		Dictionary dict = new Dictionary("testing\\dict.txt", "testing\\user.txt");
		
		log(dict.match("我覺得"));
	}
}
