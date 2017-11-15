package com.Analysis;

import java.util.*;

import com.Library.LibraryIO;
import com.Library.LibraryUtil;

public class PatternMap {
	static final String PatternMapPath = "data\\pattern\\response\\ResponseMap.dat";
	
	static HashMap<String, String> mPatternMap = initMap();
	
	public static HashMap<String, String> initMap() {
		HashMap<String, String> map = new HashMap<>();
		String[] content = LibraryIO.readFileLines(PatternMapPath);
		
		for (String line: content) {
			line = LibraryUtil.removeComment(line);
			if (line.isEmpty()) continue;
			
			String[] seg = line.split(" ");
			map.put(seg[0].toLowerCase(), seg[1]);
		}
		
		return map;
	}
	
	public static String mathType(String type) {
		return mPatternMap.get(type.toLowerCase());
	}
	
	public static void main(String[] args) {
		System.out.println(mathType("GameDifficult"));
	}
}
