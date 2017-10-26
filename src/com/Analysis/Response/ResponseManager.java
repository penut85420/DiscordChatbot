package com.Analysis.Response;

import java.util.ArrayList;

import com.Analysis.Matchers;
import com.Library.LibraryIO;
import com.Library.LibraryUtil;

public class ResponseManager {
	static final String ResponsePatternPath = "data\\pattern\\response\\ResponsePattern.dat";
	
	ArrayList<ResponseSrc> mResponse = new ArrayList<>();
	
	public ResponseManager() {
		String[] lines = LibraryIO.readFileLines(ResponsePatternPath);
		
		for (String line: lines) {
			line = LibraryUtil.removeComment(line);
			if (line.isEmpty()) continue;
			
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
		m.add("game", "踩地雷");
		m.add("player", "↖煞氣a小白↘");
		m.add("info", "VR版");
		
		ResponseManager nr = new ResponseManager();
		System.out.println(nr.toString());
		// nr.getResponse("GameDifficult", 0, m);
		// nr.getResponse("GameDifficult", 3, m);
		// nr.getResponse("GameDifficult", 5, m);
		// nr.getResponse("GameDifficult", 7, m);
	}
}
