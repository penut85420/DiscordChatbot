package com.Analysis.Acting;

import com.Analysis.Matchers;

public class ActingMatch {
	String mMatchType;
	Matchers mMatchers;
	
	public ActingMatch(String type, Matchers matchers) {
		mMatchType = type;
		mMatchers = matchers;
	}
	
	public String getType() { return mMatchType; }
	public Matchers getMatchers() { return mMatchers; }
	
	public String toString() {
		return "MatchType: " + mMatchType + "\n" + mMatchers.toString();
	}
}
