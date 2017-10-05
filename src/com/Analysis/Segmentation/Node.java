package com.Analysis.Segmentation;

import java.util.HashMap;
// import static com.Library.LibraryUtil.log;

class Node {
	String mToken;
	boolean mIsEnd;
	HashMap<String, Node> mChild = new HashMap<>();
	
	public Node(String token) {
		mToken = token;
		mIsEnd = true;
	}
	
	public Node(String token, boolean isEnd) {
		mToken = token;
		mIsEnd = isEnd;
	}
	
	public void add(String s) {
		if (s.length() <= 0) return ;
		
		if (s.length() == 1) {
			mChild.put(s, new Node(s));
			return ;
		}
		
		String first = s.substring(0, 1);
		if (mChild.get(first) == null)
			mChild.put(first, new Node(first, false));
		mChild.get(first).add(s.substring(1));
	}
	
	public String match(String s, String lastCompleteMatch, String lastLongestMatch) {
		System.out.printf("%s %s %s\n", s, lastCompleteMatch, lastLongestMatch);
		
		if (s.length() == 1) {
			if (mIsEnd) lastCompleteMatch = lastLongestMatch;
			// log("Length = 1 Return");
			return lastCompleteMatch;
		}
		
		String second = s.substring(1, 2);
		
		if (mIsEnd) lastCompleteMatch = lastLongestMatch;
		
		if (mChild.get(second) == null) {
			// log("Second Null Return");
			return lastCompleteMatch;
		}
		
		lastLongestMatch += second;
		
		return mChild.get(second).match(s.substring(1), lastCompleteMatch, lastLongestMatch);
	}
	
	public String toString() {
		String s = "  " + mToken + " " + mIsEnd;
		for (Node n: mChild.values())
			s += "\n" + n.toString();
		return s;
	}
}
