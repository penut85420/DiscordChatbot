package com.DataModule;

import java.io.*;

import com.Library.LibraryIO;

public class WordListMaker {
	public static void main(String[] args) {
		String out = "";
		File folder = new File("data\\pattern");
		for (File f: folder.listFiles()) {
			if (f.isDirectory()) continue;
			if (f.getName().contains(".dat")) continue;
			if (f.getName().contains(".txt")) continue;
			out += "-" + f.getName() + "\n";
			String[] content = LibraryIO.readFile(f).replaceAll("\r", "").split("[\n ]");
			for (String s: content) out += s + "\n";
			out += "\n";
		}
		LibraryIO.writeFile("data\\pattern\\WordList.txt", out);
		System.out.println("Done.");
	}
}
