package com.Analysis.Acting;

import java.util.*;
import static com.Library.LibraryUtil.log;

import com.Analysis.Matchers;
import com.Analysis.Pair;

public class ActingSrc {
	String mPatternType;
	ArrayList<Pair> mPair;

	public ActingSrc(String type) {
		mPatternType = type;
		mPair = new ArrayList<>();
	}

	public void addPair(String token) {
		String tag = token.substring(0, 1);
		String word = token.substring(1);
		mPair.add(new Pair(tag, word));
	}

	public ActingMatch match(String sentence) {
		String[] word = sentence.split(" ");
		int[][] result = new int[mPair.size() + 1][word.length + 1];
		String[][] dimension = new String[mPair.size() + 1][word.length + 1];
		
		for (int i = 0; i < mPair.size() + 1; i++)
			result[i][0] = 0;

		for (int i = 0; i < word.length + 1; i++)
			result[0][i] = 0;

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
			}
		}

		int patternLength = 0;
		for (int i = 0; i < mPair.size(); i++) {
			if (!mPair.get(i).getTag().equals("@"))
				patternLength++;
		}

		if (result[mPair.size()][word.length] == patternLength) {
			log("[Acting] Pattern Match: " + mPatternType + "\n");

			for (int e = 0; e < mPair.size(); e++) {
				if (mPair.get(e).getTagName() == "player") {
					String temp = "";
					for (int u = 0; u < word.length; u++)
						temp = temp + word[u];
				}
			}
			
			Matchers m = new Matchers();
			ArrayList<String> AA = new ArrayList<String>();
			int i = mPair.size();
			int j = word.length;
			int tempSlotSpot = 0;
			boolean startGetSlot = false;
			
			while (i > 0 && j > 0) {
				if (dimension[i][j].equals("C")) {
					i--;
					j--;
					if (i != 0 && j != 0 && !dimension[i][j].equals("C") && mPair.get(i - 1).getTag().equals("@"))
						tempSlotSpot = i - 1;
					startGetSlot = true;
				} else if (dimension[i][j].equals("U")) {
					i--;
				} else {
					if (startGetSlot)
						AA.add(word[j - 1]);
					j--;
					if (!dimension[i][j].equals("L")) {
						String tempSlot = "";
						for (int y = AA.size() - 1; y > -1; y--)
							tempSlot += AA.get(y);
						AA.clear();

						System.out.println(
								"[ActingSrc] Add (" + mPair.get(tempSlotSpot).getTagName() + ", " + tempSlot + ")");
						m.add(mPair.get(tempSlotSpot).getTagName(), tempSlot);
						startGetSlot = false;
					}
				}
			}
			return new ActingMatch(mPatternType, m);
		}

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