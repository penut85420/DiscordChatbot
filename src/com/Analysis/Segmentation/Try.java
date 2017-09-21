package com.Analysis.Segmentation;

import java.util.*;

import com.Library.LibraryIO;
import static com.Library.LibraryUtil.log;

public class Try {
	final static String INPUT = "testing\\input.txt";
	final static String SMALL = "testing\\small.txt";
	
	static HashMap<String, Node> list = new HashMap<>();
	
	public static void main(String[] args) {
		String[] small = LibraryIO.readFileLines(SMALL);
		for (String token: small) {
			if (token.length() == 1) {
				list.put(token, new Node(token));
				continue;
			}
			
			String first = token.substring(0, 1);
			list.get(first).add(token.substring(1));
		}
		
		String[] input = LibraryIO.readFileLines(INPUT);
		for (String i: input) {
			log(MaxMatch(i));
		}
	}
	
	public static String MaxMatch(String s) {
		int i = 0;
		String c;
		String r = "";
		
		while (i < s.length()) {
			c = getChar(s, i);
			Node n = list.get(c);
			String ss = n.match(s.substring(i));
			r += ss + " ";
			i += ss.length();
		}
		
		return r;
	}
	
	public static String getChar(String s, int i) {
		return s.substring(i, i + 1);
	}
}
