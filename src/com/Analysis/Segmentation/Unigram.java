package com.Analysis.Segmentation;

public class Unigram {
	static final String SEN = "為他的確實行動作了解釋";
	
	public static void main(String[] args) {
		unitTest();
	}
	
	public static void unitTest() {
		log(seg(SEN));
	}
	
	public static String seg(String s) {
		for (int i = 0; i < s.length(); i++) {
			log(mm(s.substring(0, i) + mm(s.substring(i))) + "\n");
		}
		
		return null;
	}
	
	public static void log(Object obj) {
		System.out.print(obj);
	}
	
	public static String mm(String s) {
		return WordSegmentation.MaximumMatch(s);
	}
}
