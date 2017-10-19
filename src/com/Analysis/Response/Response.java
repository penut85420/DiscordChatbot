package com.Analysis.Response;

import com.Library.LibraryIO;

public class Response {
	static final String ResponsePath = "data\\pattern\\response.dat";
	
	public Response() {
		String[] lines = LibraryIO.readFileLines(ResponsePath);
		
		for (String line: lines) {
			if (line.startsWith("//")) continue;
			
			
		}
	}
	
	public static void main(String[] args) {
		
	}
}
