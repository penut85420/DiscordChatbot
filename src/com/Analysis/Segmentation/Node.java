package com.Analysis.Segmentation;

import java.util.HashMap;

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
		if (s.length() == 1) {
			mChild.put(s, new Node(s));
			return ;
		}
		
		String first = s.substring(0, 1);
		if (mChild.get(first) == null)
			mChild.put(first, new Node(first, false));
		mChild.get(first).add(s.substring(1));
	}
	
	public String match(String s) {
		if (!s.startsWith(mToken)) return "";
		
		if (s.length() == 1) return s;
		
		String second = s.substring(1, 2);
		if (mChild.get(second) == null) return s.substring(0, 1);
		
		return s.substring(0, 1) + mChild.get(second).match(s.substring(1));
	}
	
	public String toString() {
		String s = mToken + mIsEnd + "\n";
		for (Node n: mChild.values())
			s += n.toString() + "\n";
		return s;
	}
}