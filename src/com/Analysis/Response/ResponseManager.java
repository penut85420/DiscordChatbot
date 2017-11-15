package com.Analysis.Response;

import java.util.ArrayList;
import java.util.HashMap;

import com.Analysis.Matchers;
import com.Library.*;
import static com.Library.LibraryUtil.log;

@SuppressWarnings("unused")
public class ResponseManager {
	static final String ResponsePatternPath = "data\\pattern\\response\\ResponsePattern.dat";
	
	// 所有Response結構的集合
	static HashMap<String, ArrayList<ResponseSrc>> mResponseMap = init();
	
	// 讀取ResponsePattern檔案
	public static HashMap<String, ArrayList<ResponseSrc>> init() {
		HashMap<String, ArrayList<ResponseSrc>> map = new HashMap<>();
		ArrayList<ResponseSrc> list = new ArrayList<>();
		String[] lines = LibraryIO.readFileLines(ResponsePatternPath);
		String type = null;
		
		for (String line: lines) {
			// 前置Comment處理
			line = LibraryUtil.removeComment(line);
			if (line.isEmpty()) continue;
			
			// Load Pattern Type
			if (line.startsWith("-")) {
				type = line.substring(1);
				list = new ArrayList<>();
				map.put(type.toLowerCase(), list);
				continue;
			}
			
			// Pattern格式斷開
			String[] seg = line.split(" ");
			
			// 獲得情緒指數的高低值
			int[] bound = getBound(seg[0]);
			ResponseSrc rs = new ResponseSrc(bound[0], bound[1], type);
			
			// 將Pattern的每個Seg加入Pattern結構中
			for (int i = 1; i < seg.length; i++) 
				rs.addPair(seg[i].substring(0, 1), seg[i].substring(1));
			
			// 將Pattern結構加入Pattern集合中
			list.add(rs);
		}
		
		return map;
	}
	
	// 將情緒指數高低的字串格式轉成兩個整數，Ex: "0-9" > {0, 9}
	private static int[] getBound(String s) {
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
		for (ArrayList<ResponseSrc> arr: mResponseMap.values())
			for (ResponseSrc rs: arr)
				s += rs.toString() + "\n\n";
		return s;
	}
	
	// 產生隨機的Response
	public static String getResponse(String type, int emotion, Matchers matchers) {
		ArrayList<ResponseSrc> arr = mResponseMap.get(type.toLowerCase());
		if (arr == null) return null;
		
		int r = LibraryMath.getRandNum(arr.size());
		return arr.get(r).makeReponse(matchers);
	}
	
	public static void main(String[] args) {
		Matchers m = new Matchers();
		m.add("game", "踩地雷");
		m.add("player", "↖煞氣a小白↘");
		m.add("info", "VR版");
		
		getResponse("GameDifficultReply", 0, m);
//		nr.getResponse("GameDifficultReply", 3, m);
//		nr.getResponse("GameDifficultReply", 5, m);
//		nr.getResponse("GameDifficultReply", 7, m);
		System.out.println("Done");
	}
}
