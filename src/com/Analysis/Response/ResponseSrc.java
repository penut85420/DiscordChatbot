package com.Analysis.Response;

import java.util.*;

import com.Analysis.Pair;

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
	
	@Deprecated
	public void addPair(String[] pair, String tag) {
		mPair.add(new Pair(pair, tag));
	}
	
	public void addPair(String tag, String word) {
		mPair.add(new Pair(tag, word));
	}
	
	public String toString() {
		String s = String.format("[%d-%d] %s {\n", mFriendlyLowBound, mFriendlyHighBound, mResponseType);
		for (Pair p : mPair)
			s += "  " + p.toString() + "\n";
		s += "}";
		return s;
	}
}
