package com.DataModule;

import java.util.ArrayList;

import com.Library.LibraryIO;

public class SortUserDictionary {
	public static void main(String[] args) {
		String[] word = LibraryIO.readFileLines("data\\dictionary\\dictionary_user.txt");
		String[] list = LibraryIO.readFileLines("data\\pattern\\wordlist.txt");
		ArrayList<String> arr = new ArrayList<>();
		
		for (String s: list) {
			if (s.startsWith("-")) continue;
			if (s.isEmpty()) continue;
			arr.add(s);
		} 
		
		for (String s: word) arr.add(s);
		
		arr.sort(null);
		
		String out = "", pre = "";
		for (String s: arr) {
			if (s.equals(pre)) continue;
			out += s + "\r\n";
			pre = s;
		}
		LibraryIO.writeFile("data\\dictionary\\dictonary_user_sort.txt", out);
		
		System.out.println("done");
	}
}
