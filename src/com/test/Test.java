package com.test;

import java.util.*;

public class Test {
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date("2017/10/1"));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(dayOfWeek);
	}
}
