package com.Database.getConstDataFromSteamAPI;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.Database.DataBaseManager;

public class SteamCrawler {
	DataBaseManager dbm;
	
	private final static String appListAdress = "http://api.steampowered.com/ISteamApps/GetAppList/v2"; 
	private final static String appListName = "appList.json";
	
	public SteamCrawler() {
		dbm = new DataBaseManager();
		
	}
	
	private void getList () {
		try {
			URL website = new URL(appListAdress);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(appListName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String arg0[]) {
		
	}
}
