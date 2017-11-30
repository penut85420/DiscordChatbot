package com.Database.getConstDataFromSteamAPI;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.json.*;

import com.Database.DataBaseManager;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

public class SteamCrawler {
	DataBaseManager dbm;
	
	private final static String appListAdress = "http://api.steampowered.com/ISteamApps/GetAppList/v2"; 
	private final static String appListName = "appList";
	
	public SteamCrawler() {
		dbm = new DataBaseManager();
		
	}
	
	private void getList () {
		/*try {
			URL website = new URL(appListAdress);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(appListName + ".json");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String temp = "";
		try {
			BufferedReader mbr = new BufferedReader(new InputStreamReader(new FileInputStream(appListName + ".json"), "UTF-8"));
			while (mbr.ready())
				temp += mbr.readLine();
			mbr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject mJSON = new JSONObject(temp);
		JSONArray mJArray = mJSON.getJSONObject("applist").getJSONArray("apps");
		
		for(int i = 0; i < mJArray.length(); i++){
			JSONObject j = mJArray.getJSONObject(i);
			dbm.insert("games (game_id, game_name)", "(\'"
					+ j.getInt("appid") + "\',\'" + SpecChar(j.getString("name")) +"\')");
			if(i%1000 == 0)System.out.println(i);
		}
	}
	
	private String SpecChar(String s){
		return s.replaceAll("'", "\\\\'");
	}
	
	public static void main(String arg0[]) {
		SteamCrawler sc = new SteamCrawler();
		sc.getList();
		String s = "The Wizard's Pen";
		int i = 3580;
		//sc.dbm.insert("games (game_id, game_name)", "(\'"
		//			+ i + "\',\'" + sc.SpecChar(s) +"\')");
		//System.out.println(sc.SpecChar(s));
	}
}
