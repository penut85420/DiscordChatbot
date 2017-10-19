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
			if (token.length() < 1) continue;
			if (token.length() == 1 && mDictionaryMap.get(token) == null) {
				mDictionaryMap.put(token, new Node(token));
				continue;
			}
			
			String first = token.substring(0, 1); 
			if (mDictionaryMap.get(first) == null)
				mDictionaryMap.put(first, new Node(first, false));
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
			s += key + " {\n";
			s += mDictionaryMap.get(key).toString();
			s += "\n}\n\n";
		}
			
		return s;
	}
	
	public static void main(String[] args) {
		Dictionary dict = new Dictionary("data\\dictionary\\dictionary_small.txt", null);
		
		log(dict);
	}
}
