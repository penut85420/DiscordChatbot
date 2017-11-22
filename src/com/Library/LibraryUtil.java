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
}
