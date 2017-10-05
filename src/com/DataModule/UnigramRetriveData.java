package com.DataModule;

import java.io.FileOutputStream;

import com.Library.LibraryIO;

public class UnigramRetriveData {
	public static void main(String[] args) throws Exception {
		String[] content = LibraryIO.readFileLines("testing\\unigram.txt");
		FileOutputStream fout = new FileOutputStream("testing\\dictdict.txt");
		fout.write(new String("\uFEFF").getBytes("UTF-8"));
		for (String s: content) {
			String[] ss = s.split("\t");
			fout.write(new String(ss[0] + "\n").getBytes("UTF-8"));
		}
		fout.close();
		System.out.println("Done.");
	}
}
