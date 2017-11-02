package com.Analysis.Segmentation;

import java.util.*;

public class Unigram {
	static final String SEN = "為他的確實行動作了解釋";
	
	public static void main(String[] args) {
		unitTest();
	}
	
	// 單元測試
	public static void unitTest() {
		log("Test\n");
		// seg2(SEN);
		seg(SEN);
	}
	
	public static String seg(String s) {
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		
		int len = s.length();
		for (int i = 0; i < len; i++) {
			ArrayList<String> segList = new ArrayList<>();
			for (int j = i; j < len; j++) {
				String nowSeg = s.substring(i, j + 1);
				if (nowSeg.equals(mm(nowSeg)))
					segList.add(nowSeg.trim());
			}
			list.add(segList);
		}
		
//		log("Display List\n");
//		for (ArrayList<String> segList: list) { 
//			for (String seg: segList)
//				log(seg + " ");
//			log("\n");
//		}
		
		foo(list, 0, "");
		
		return null;
	}
	
	private static void foo(ArrayList<ArrayList<String>> list, int index, String now) {
		// log("index: " + index + " ");
		if (index == list.size()) { log("[" + now.trim() + "]\n"); return; }
		if (index > list.size()) return;
		
		for (String seg: list.get(index)) {
			// log(now + ", " + seg + ", " + seg.length() + "\n");
			foo(list, index + seg.length(), now + seg + " ");
		}
	}
	
	// =====Sugar=====
	
	public static void log(Object obj) {
		System.out.print(obj);
	}
	
	public static String mm(String s) {
		return WordSegmentation.MaximumMatch(s);
	}
}
