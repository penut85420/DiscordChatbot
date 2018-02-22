package com.InternetResource;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import org.json.JSONObject;

import com.Database.DataBaseManager;
import com.Library.LibraryIO;
import static com.Library.LibraryUtil.logln;

public class SteamDeal {
	final static String TempFilePath = "testing\\temp.json",
						SteamDetailUrl = "http://store.steampowered.com/api/appdetails?appids=",
						SteamGameLinkBase = "http://store.steampowered.com/app/",
						ColData = "Data",
						ColPriceOverview = "price_overview",
						ColDiscount = "discount_percent",
						ColName = "name";

	public static String isGameInDeal(String title) {
		String discountMessage = null;
		try {
			ArrayList<String> idList = DataBaseManager.matchGameTitle(title);
			for (String id : idList) {
				// Read Appdetail JSON
				URL gameInfo = new URL(SteamDetailUrl + id);
				ReadableByteChannel rbc = Channels.newChannel(gameInfo.openStream());
				FileOutputStream fos = new FileOutputStream(TempFilePath);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();

				String temp = LibraryIO.readFile(TempFilePath);

				// JSON Parsing
				JSONObject data = new JSONObject(temp);
				System.out.println(data);
				
				if (data.isNull(id)) continue;
				if (data.getJSONObject(id).isNull(ColData)) continue;
				
				data = data.getJSONObject(id).getJSONObject(ColData);
				System.out.println(data);
				
				if (data.isNull(ColPriceOverview)) continue;
				
				// Retrive Discount & Game Title
				int discountRate = data.getJSONObject(ColPriceOverview).getInt(ColDiscount);
				if (discountRate == 0) continue; // Skip If No Discount
				String gameTitle = data.getString(ColName);

				// TODO: 處理多個特價的狀況
				discountMessage = discountRate + "%;" + gameTitle + ";" + SteamGameLinkBase + id + "\n";
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return discountMessage;
	}

	/*
	 * public void getSalePage() { try { URL website = new URL(steamSaleUrl);
	 * ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	 * FileOutputStream fos = new FileOutputStream(filename);
	 * fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); fos.close(); }
	 * catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * public void parse() { String s = "";
	 * 
	 * try { BufferedReader mbr = new BufferedReader(new InputStreamReader(new
	 * FileInputStream(filename), "UTF-8")); while (mbr.ready()) s +=
	 * mbr.readLine(); mbr.close(); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * 
	 * s = s.substring(s.indexOf("</channel>") + "</channel>".length(),
	 * s.indexOf("</rdf:RDF>")); int i; String date = "2017-11-15"new
	 * SimpleDateFormat("yyyy-MM-dd").format(new Date());
	 * 
	 * while ((i = s.indexOf("<dc:date>" + date)) != -1) { String contents =
	 * s.substring(0, i); String title =
	 * contents.substring(contents.indexOf("<title><![CDATA[") +
	 * "<title><![CDATA[".length(), contents.indexOf("]]></title>")); contents =
	 * s.substring(i, s.indexOf("</item>")); String id = ""; try { id =
	 * contents.substring(contents.indexOf("<steam:appid>") +
	 * "<steam:appid>".length(), contents.indexOf("</steam:appid>")); } catch
	 * (Exception e) { } System.out.println(title + "\n" + id);
	 * 
	 * 
	 * dbc.insert("game_deal (game_id,deal_title)", "(\'" + id + "\',\'" + title
	 * +"\')");
	 * 
	 * //dbc.insert("", "("); // dataList.add(new data(title, id));
	 * 
	 * s = s.substring(s.indexOf("</item>") + "</item>".length()); } }
	 */

	public static void main(String arg0[]) {
		System.out.println(SteamDeal.isGameInDeal("Bounty Train"));
	}
}
