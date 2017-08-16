package com.Library;

public class LibraryMath {
	public static int getRandNum(int up) {
		return getRandNum(0, up);
	}
	
	public static int getRandNum(int low, int up) {
		return (int)(Math.random() * up - low) + low;
	}
}
