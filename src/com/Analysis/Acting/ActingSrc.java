package com.Analysis.Acting;

import java.util.*;
import static com.Library.LibraryUtil.log;
import com.Analysis.Pair;

public class ActingSrc {
	String mPatternType;
	ArrayList<Pair> mPair;

	public ActingSrc(String type) {
		mPatternType = type;
		mPair = new ArrayList<>();
	}
	
	public void addPair(String tag, String word) {
		mPair.add(new Pair(tag, word));
	}

	public ActingMatch match(String sentence) {
		String[] word = sentence.split(" ");
		int[][] result = new int[mPair.size() + 1][word.length + 1];
		String[][] dimension = new String[mPair.size() + 1][word.length + 1];
		for (int i = 0; i < mPair.size() + 1; i++) {
			result[i][0] = 0;
		}

		for (int i = 0; i < word.length + 1; i++) {
			result[0][i] = 0;
		}

		boolean matched = false;
		for (int i = 1; i < mPair.size() + 1; i++) {
			for (int j = 1; j < word.length + 1; j++) {
				matched = mPair.get(i - 1).isMatch(word[j - 1]);
				result[i][j] = result[i - 1][j];
				dimension[i][j] = "U";
				if (result[i][j] < result[i][j - 1]) {
					result[i][j] = result[i][j - 1];
					dimension[i][j] = "L";
				}
				if (matched && result[i][j] < result[i - 1][j - 1] + 1) {
					result[i][j] = result[i - 1][j - 1] + 1;
					dimension[i][j] = "C";
				}
				// if (result[i - 1][j - 1] + 1 > result[i - 1][j] && result[i -
				// 1][j - 1] + 1 > result[i][j - 1]
				// && matched)
				// result[i][j] = result[i - 1][j - 1] + 1;
				// else if (result[i - 1][j] == result[i][j - 1])
				// result[i][j] = result[i - 1][j];
				// else if (result[i - 1][j] > result[i][j - 1])
				// result[i][j] = result[i - 1][j];
				// else
				// result[i][j] = result[i][j - 1];
			}
		}

		log(mPatternType + "\n");
		log(Arrays.toString(word) + "\n");
		
		for (int[] i : result) {
			for (int j : i) {
				log(j + " ");
			}
			log("\n");
		}
		
		for (String[] i : dimension) {
			for (String j : i) {
				log(j + " ");
			}
			log("\n");
		}
		
		int patternLength = 0;
		for (int i = 0; i < mPair.size(); i++) {
			if (!mPair.get(i).getTag().equals("@"))
				patternLength++;
		}

		if (result[mPair.size()][word.length] == patternLength) {
			log("MATCH!!\n");
			// int[] temp = new int[10];
			ArrayList<String> AA = new ArrayList<String>();
			int i = mPair.size();
			int j = word.length;
			// int x = 0;
			while (i > 0 && j > 0) {
				if (dimension[i][j].equals("C")) {
					i--;
					j--;
				} else if (dimension[i][j].equals("U")) {
					i--;
				} else if (dimension[i][j].equals("L")) {
					/*
					 * temp[x]=j; x++;
					 */
					AA.add(word[j - 1]);
					j--;
				}

			}
			
			for (int y = AA.size() - 1; y > -1; y--) {
				// log(word[temp[y]-1]+"\n");
				log(AA.get(y) + " ");
			}
			log("\n");
		}
		log("\n");
		return null;
	}

	public String toString() {
		String s = mPatternType + " {\n";
		for (Pair p : mPair)
			s += "  " + p.toString() + "\n";
		s += "}";
		return s;
	}
}