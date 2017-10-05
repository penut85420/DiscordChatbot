package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

import com.Library.LibraryIO;

public class Test {

	public static void main(String[] args) {
		String[] dict = LibraryIO.readFileLines("testing\\dictdict.txt");
		Arrays.sort(dict);
		File file = new File("testing\\dict_sort.txt");
		try {
			if (!file.exists()) 
				file.getParentFile().mkdir();
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(new String("\uFEFF").getBytes("UTF-8"));
			for (String s: dict)
				fout.write(new String(s + "\n").getBytes("UTF-8"));
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
