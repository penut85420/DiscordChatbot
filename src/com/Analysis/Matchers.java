package com.Analysis;

import java.util.*;

public class Matchers {
	HashMap<String, String> mMatch = new HashMap<>();
	
	public void add(String key, String value) {
		mMatch.put(key.trim().toLowerCase(), value);
	}
	
	public String get(String key) {
		return mMatch.get(key.trim().toLowerCase());
	}
	
	public String toString() {
		String s = "";
		for (String key: mMatch.keySet())
			s += "Key: " + key + ", Value: " + mMatch.get(key) + "\n";
		return s;
	}
}
