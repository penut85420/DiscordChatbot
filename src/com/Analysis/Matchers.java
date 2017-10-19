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
}
