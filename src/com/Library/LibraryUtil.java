package com.Library;

public class LibraryUtil {
	public static void log(Object obj) {
		if (obj == null)
			System.out.println("[null]");
		else System.out.println(obj.toString());
	}
}
