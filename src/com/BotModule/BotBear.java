package com.BotModule;

import com.Library.LibraryMath;

public class BotBear {
	String[] term = {"...", "OAO", "...OAO"};
	
	public String[] response(String msg) {
		String[] sReturns = new String[1];
		String sReturn = "";
		
		sReturn += term[LibraryMath.getRandNum(term.length)];
		
		if (msg.indexOf("躲") >= 0 || msg.indexOf("閃") >= 0)
			sReturn += "(黏黏";
		
		sReturns[0] = sReturn;
		
		return sReturns;
	}
}
