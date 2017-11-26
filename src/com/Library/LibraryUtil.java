package com.Library;

import java.util.Arrays;

public class LibraryUtil {
	public static void main(String[] args) {
		System.out.println(removeComment("-GameDifficult"));
	}
	
	public static void log(Object obj) {
		if (obj == null)
			System.out.print("[null]");
		else System.out.print(obj.toString());
	}
	
	public static String clipBracket(String s) {
		return "[" + s + "]";
	}
	
	public static String removeComment(String s) {
		if (s.contains("//")) 
			s = s.substring(0, s.indexOf("//"));
		return s.trim();
	}
	
	public static void logArr(Object[] objarr) {
		log(Arrays.toString(objarr).replaceAll(", ", "\n").replace("[", "").replace("]", ""));
	}
	
	// 將情緒指數高低的字串格式轉成兩個整數，Ex: "0-9" > {0, 9}
	public static int[] getBound(String s) {
		try {
			String[] ss = s.split("-");
			int[] bound = new int[] {Integer.parseInt(ss[0]), Integer.parseInt(ss[1])};
			
			if (bound[0] > bound[1]) {
				int tmp = bound[0];
				bound[0] = bound[1];
				bound[1] = tmp;
			}
			
			return bound;
		} catch (Exception e) {
			System.out.println("[LibraryUtil] getBound: Can't parse string [" + s + "] into bound");
			return null; 
		}
	}
}
