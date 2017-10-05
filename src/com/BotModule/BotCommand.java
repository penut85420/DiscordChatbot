package com.BotModule;

import java.util.*;

public class BotCommand {
	static String COMMAND_TAG = "/";
	
	static HashMap<String, Runnable> mCmdFunc = initCmd();
	
	public static HashMap<String, Runnable> initCmd() {
		HashMap<String, Runnable> cmd = new HashMap<>();
		cmd.put("exit", () -> {System.exit(0);});
		
		return cmd;
	}
	
	public static boolean isCmd(String msg) {
		return msg.startsWith(COMMAND_TAG);
	}
	
	public static boolean doCmd(String msg) {
		msg = msg.substring(1).toLowerCase();
		Runnable r = mCmdFunc.get(msg);
		
		if (r == null) return false;
		
		r.run();
		return true;
	}
}
