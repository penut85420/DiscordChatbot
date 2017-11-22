package com.Analysis;

import java.util.*;

import com.Library.LibraryIO;
import com.Library.LibraryUtil;

public class PatternMap {
	static final String PatternMapPath = "data\\pattern\\response\\ResponseMap.dat";
	
	static HashMap<String, Type> mPatternMap = initMap();
	
	public static HashMap<String, Type> initMap() {
		HashMap<String, Type> map = new HashMap<>();
		String[] content = LibraryIO.readFileLines(PatternMapPath);
		
		for (String line: content) {
			line = LibraryUtil.removeComment(line);
			if (line.isEmpty()) continue;
			
			String[] seg = line.split(" ");
			map.put(seg[0].toLowerCase(), new Type(seg[1]));
		}
		
		return map;
	}
	
	public static String matchType(String type) {
		Type t = mPatternMap.get(type.toLowerCase());
		if (t == null) return null;
		return t.getType();
	}
	
	public static boolean isProcess(String type) {
		Type t = mPatternMap.get(type.toLowerCase());
		if (t == null) return false;
		return t.isProcess();
	}
	
	public static void main(String[] args) {
		System.out.println(matchType("GameDifficult"));
	}
	
	static class Type {
		String mType;
		boolean isMethod = false;
		
		public Type(String type) {
			if (type.startsWith("$")) {
				isMethod = true;
				type = type.substring(1);
			} mType = type;
		}
		
		public String getType() { return mType; }
		public boolean isProcess() { return isMethod; }
	}
}
