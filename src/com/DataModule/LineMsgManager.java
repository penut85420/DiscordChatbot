package com.DataModule;

import com.Analysis.Segmentation.WordSegmentation;
import com.Library.LibraryIO;

public class LineMsgManager {
	public static void main(String[] args) {
		eraseTimestamp("sample\\chat\\ChatSample_0001.txt", "sample\\chat\\ChatSample_0001_NoTimestamp.txt");
		getUserMsg("小熊", "sample\\chat\\ChatSample_0001.txt", "sample\\chat\\ChatSample_0001_Bear.txt");
		getUserMsg("陳威廷", "sample\\chat\\ChatSample_0001.txt", "sample\\chat\\ChatSample_0001_Penut.txt");
		
		segMsg("sample\\chat\\ChatSample_0001_NoTimestamp.txt", "sample\\chat\\ChatSample_0001_seg.txt");
		segMsg("sample\\chat\\ChatSample_0001_Bear.txt", "sample\\chat\\ChatSample_0001_Bear_seg.txt");
		segMsg("sample\\chat\\ChatSample_0001_Penut.txt", "sample\\chat\\ChatSample_0001_Penut_seg.txt");
	}
	
	public static void getUserMsg(String name, String fin, String fout) {
		String[] msg = LibraryIO.readFileLines(fin);
		String content = "";

		for (String s: msg) {
			if (s.split(" ").length > 1)
			if (s.split(" ")[1].equals(name))
				content += s.substring(s.indexOf(" ")) + "\n";
		}

		LibraryIO.writeFile(fout, content);
	}
	
	public static void eraseTimestamp(String fin, String fout) {
		String[] msg = LibraryIO.readFileLines(fin);
		String content = "";

		for (String s: msg)
			if (s.indexOf(" ") >= 0)
				content += s.substring(s.indexOf(" ")) + "\n";

		LibraryIO.writeFile(fout, content);
	}
	
	public static void segMsg(String fin, String fout) {
		String[] sample = LibraryIO.readFileLines(fin);
		String content = "";
		for (String s: sample)
			content += WordSegmentation.MaximumMatch(s) + "\n";
		LibraryIO.writeFile(fout, content);
	}
}
