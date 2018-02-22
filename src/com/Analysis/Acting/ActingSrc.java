package com.Analysis.Acting;

import java.util.*;
import static com.Library.LibraryUtil.log;
import static com.Library.LibraryUtil.logln;

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
		Integer pairSize = mPair.size(), wordLength = word.length, i, j;
		Integer[][] result = new Integer[pairSize + 1][wordLength + 1];
		String[][] dimension = new String[pairSize + 1][wordLength + 1];
		
		// Matrix Init
		for (i = 0; i < pairSize + 1; i++)
			result[i][0] = 0;

		for (i = 0; i < wordLength + 1; i++)
			result[0][i] = 0;

		boolean isMatched = false;
		for (i = 1; i < pairSize + 1; i++) {
			for (j = 1; j < wordLength + 1; j++) {
				isMatched = mPair.get(i - 1).isMatch(word[j - 1]);
				result[i][j] = result[i - 1][j];
				dimension[i][j] = "U";

				if (result[i][j] < result[i][j - 1]) {
					result[i][j] = result[i][j - 1];
					dimension[i][j] = "L";
				}
				
				if (isMatched && result[i][j] < result[i - 1][j - 1] + 1) {
					result[i][j] = result[i - 1][j - 1] + 1;
					dimension[i][j] = "C";
				}
			}
		}

		// Count Necessary Token Length
		int patternLength = 0;
		for (i = 0; i < pairSize; i++) {
			if (mPair.get(i).isTagSlot()) continue;
			if (mPair.get(i).isTagOption()) continue;
			patternLength++;
		}
		
//		logln(toString());
//		logd(result);
//		logln("");

		// If Matched Then Find The Slot
		if (result[pairSize][wordLength] >= patternLength) {			
			Matchers m = new Matchers();
			ArrayList<String> arr = new ArrayList<String>();
			i = mPair.size(); j = word.length;
			
			int tempSlotSpot = 0;
			boolean startGetSlot = false;
			
			while (i > 0 && j > 0) {
				if (dimension[i][j].equals("C")) {
					i--; j--;
					if (i != 0 && j != 0 && !dimension[i][j].equals("C"))
					if (mPair.get(i - 1).isTagSlot())
						tempSlotSpot = i - 1;
					startGetSlot = true;
				} else if (dimension[i][j].equals("U")) {
					i--;
				} else {
					if (startGetSlot)
						arr.add(word[j - 1]);
					j--;
					if (!dimension[i][j].equals("L")) {
						String tempSlot = "";
						for (int y = arr.size() - 1; y > -1; y--)
							tempSlot += arr.get(y);
						arr.clear();

						logln("[Acting] Add (\"" + mPair.get(tempSlotSpot).getTagName() + "\", \"" + tempSlot + "\")");
						m.add(mPair.get(tempSlotSpot).getTagName(), tempSlot);
						startGetSlot = false;
					}
				}
			}
			return new ActingMatch(mPatternType, m);
		}
		return null;
	}
	
	public void logd(Object[][] obj) {
		for (Object[] arr: obj) {
			for (Object i: arr)
				log((i == null? "N": i.toString()) + " ");
			log("\n");
		}
	}

	public String toString() {
		String s = mPatternType + " {\n";
		for (Pair p : mPair)
			s += "  " + p.toString() + "\n";
		s += "}";
		return s;
	}
}