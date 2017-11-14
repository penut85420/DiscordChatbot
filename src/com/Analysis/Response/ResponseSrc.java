package com.Analysis.Response;

import java.util.*;

import com.Analysis.*;

public class ResponseSrc {
	int mFriendlyLowBound;
	int mFriendlyHighBound;
	String mResponseType;
	ArrayList<Pair> mPair = new ArrayList<>();
	
	public ResponseSrc(int low, int high, String type) {
		mFriendlyLowBound = low;
		mFriendlyHighBound = high;
		mResponseType = type;
	}
	
	public void addPair(String tag, String word) {
		mPair.add(new Pair(tag, word));
	}
	
	public String getResponseType() { return mResponseType; }
	
	public String makeReponse(Matchers m) {
		String s = "";
		
		for (Pair p: mPair) {
			String seg = p.getRndPair();
			
			if (seg == null && p.isTagSlot())
				seg = m.get(p.getTagName());
			s += seg;
		}
		return s;
	}
	
	public String toString() {
		String s = String.format("[%d-%d] %s {\n", mFriendlyLowBound, mFriendlyHighBound, mResponseType);
		for (Pair p : mPair)
			s += "  " + p.toString() + "\n";
		s += "}";
		return s;
	}
}
