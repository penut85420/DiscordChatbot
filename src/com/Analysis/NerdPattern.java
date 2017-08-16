package com.Analysis;

import java.util.Arrays;
import java.util.regex.*;

public class NerdPattern {
	private static String FILE_PATTERN = "data/pattern/pattern.dat";

	public static void main(String[] args) {
		String inputStr = "ABC\nABC\nABC";
		String patternStr = "(?d)ABC";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(inputStr);
		boolean matchFound = matcher.find();
		while (matchFound) {
			System.out.println(matcher.start() + "-" + matcher.end());
			for (int i = 0; i <= matcher.groupCount(); i++) {
				String groupStr = matcher.group(i);
				System.out.println(i + ":" + groupStr);
			}
			if (matcher.end() + 1 <= inputStr.length()) {
				matchFound = matcher.find(matcher.end());
			} else {
				break;
			}
		}
	}

}
