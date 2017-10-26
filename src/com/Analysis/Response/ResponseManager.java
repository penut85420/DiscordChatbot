package com.Analysis.Response;

import java.util.ArrayList;

import com.Analysis.Matchers;
import com.Library.*;
import static com.Library.LibraryUtil.log;

@SuppressWarnings("unused")
public class ResponseManager {
	static final String ResponsePatternPath = "data\\pattern\\response\\ResponsePattern.dat";
	
	// 所有Response結構的集合
	ArrayList<ResponseSrc> mResponse = new ArrayList<>();
	
	// 讀取ResponsePattern檔案
	public ResponseManager() {
		String[] lines = LibraryIO.readFileLines(ResponsePatternPath);
		String type;
		
		for (String line: lines) {
			// 前置處理
			line = LibraryUtil.removeComment(line);
			if (line.isEmpty()) continue;
			
			// Load Pattern Type
			if (line.startsWith("-")) {
				type = line.substring(1);
				continue;
			}
			
			// Pattern格式斷開
			String[] seg = line.split(" ");
			
			// 獲得情緒指數的高低值
			int[] bound = getBound(seg[0]);
			ResponseSrc rs = new ResponseSrc(bound[0], bound[1], seg[1]);
			
			// 將Pattern的每個Seg加入Pattern結構中
			for (int i = 2; i < seg.length; i++) 
				rs.addPair(seg[i].substring(0, 1), seg[i].substring(1));
			
			// 將Pattern結構加入Pattern集合中
			mResponse.add(rs);
		}
	}
	
	// 將情緒指數高低的字串格式轉成兩個整數，Ex: "0-9" > {0, 9}
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
	
	// 將此結構的集合輸出成字串
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
