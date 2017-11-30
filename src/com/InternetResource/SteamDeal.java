package com.InternetResource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.SQLException;
import java.util.Date;

import com.Database.DataBaseManager;
import com.mysql.jdbc.DatabaseMetaDataUsingInfoSchema;

import java.text.SimpleDateFormat;

public class SteamDeal {
	private final String steamSaleUrl = "http://store.steampowered.com/feeds/daily_deals.xml";
	private final String filename = "daily_deals.xml";
	private DataBaseManager dbc;

	public SteamDeal(DataBaseManager dbc) {
		this.dbc = dbc;
	}

	public void getSalePage() {
		try {
			URL website = new URL(steamSaleUrl);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(filename);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parse() {
		String s = "";

		try {
			BufferedReader mbr = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			while (mbr.ready())
				s += mbr.readLine();
			mbr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		s = s.substring(s.indexOf("</channel>") + "</channel>".length(), s.indexOf("</rdf:RDF>"));
		int i;
		String date = "2017-11-15"/*new SimpleDateFormat("yyyy-MM-dd").format(new Date())*/;

		while ((i = s.indexOf("<dc:date>" + date)) != -1) {
			String contents = s.substring(0, i);
			String title = contents.substring(contents.indexOf("<title><![CDATA[") + "<title><![CDATA[".length(),
					contents.indexOf("]]></title>"));
			contents = s.substring(i, s.indexOf("</item>"));
			String id = "";
			try {
				id = contents.substring(contents.indexOf("<steam:appid>") + "<steam:appid>".length(),
						contents.indexOf("</steam:appid>"));
			} catch (Exception e) {
			}
			System.out.println(title + "\n" + id);
			
			
			dbc.insert("game_deal (game_id,deal_title)", "(\'" + id + "\',\'" + title +"\')");

			//dbc.insert("", "(");
			// dataList.add(new data(title, id));

			s = s.substring(s.indexOf("</item>") + "</item>".length());
		}
	}

	public static void main(String arg0[]){
		DataBaseManager db = new DataBaseManager();
		SteamDeal s = new SteamDeal(db);
		//s.getSalePage();
		//s.parse();
		
	}
}
