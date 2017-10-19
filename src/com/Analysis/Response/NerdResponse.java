package com.Analysis.Response;

import java.util.ArrayList;

import com.Analysis.Matchers;
import com.Library.LibraryIO;

public class NerdResponse {
	static final String ResponsePath = "data\\pattern\\response.dat";
	
	ArrayList<ResponseSrc> mResponse = new ArrayList<>();
	
	public NerdResponse() {
		String[] lines = LibraryIO.readFileLines(ResponsePath);
		
		for (String line: lines) {
			if (line.isEmpty()) continue;
			if (line.startsWith("//")) continue;
			
			String[] seg = line.split(" ");
			int[] bound = getBound(seg[0]);
			ResponseSrc rs = new ResponseSrc(bound[0], bound[1], seg[1]);
			
			for (int i = 2; i < seg.length; i++) 
				rs.addPair(seg[i].substring(1).split(","), seg[i].substring(0, 1));
			mResponse.add(rs);
		}
	}
	
	private int[] getBound(String s) {
		String[] ss = s.split("-");
		int[] bound = new int[] {Integer.parseInt(ss[0]), Integer.parseInt(ss[1])};
		
		if (bound[0] > bound[1]) {
			int tmp = bound[0];
			bound[0] = bound[1];
			bound[1] = tmp;
		}
		
		return bound;
	}
	
	public String toString() {
		String s = "";
		for (ResponseSrc rs: mResponse)
			s += rs.toString() + "\n\n";
		return s;
	}
	
	public static void main(String[] args) {
		Matchers m = new Matchers();
		m.add("game", "星爆氣流斬");
		
		NerdResponse nr = new NerdResponse();
		System.out.println(nr.toString());
		// nr.getResponse("GameDifficult", 0);
		// nr.getResponse("GameDifficult", 3);
		// nr.getResponse("GameDifficult", 5);
		// nr.getResponse("GameDifficult", 7);
	}
}
