package com.Library;

import java.io.*;

public class LibraryIO {
	public static String readFile(File file) {
		return readFile(file.getPath());
	}
	
	public static String readFile(String file) {
		try {
			if (!new File(file).exists()) return null;
			FileInputStream fin = new FileInputStream(file);
			byte ba[] = new byte[fin.available()];
            fin.read(ba);
            String s = new String(ba, "UTF-8");
            s = s.replace("\uFEFF", "");
            fin.close();
            return s;
		} catch (IOException e) { 
			e.printStackTrace();
			return null;
		}
	}
	
	public static void writeFile(String file, String content) {
		writeFile(new File(file), content);
	}
	
	public static void writeFile(File file, String content) {
		try {
			if (!file.exists()) 
				file.getParentFile().mkdir();
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(new String("\uFEFF" + content).getBytes("UTF-8"));
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
