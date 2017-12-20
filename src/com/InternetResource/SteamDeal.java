package com.InternetResource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import org.json.JSONObject;

import com.Database.DataBaseManager;

public class SteamDeal {
	private final static String steamDetailUrl = "http://store.steampowered.com/api/appdetails?appids=";
	private final static String steamGameLinkBase = "http://store.steampowered.com/app/";
	private String JSONToken[] = { "", "data", "price_overview", "discount_percent" };

	public static String isGameInDeal(String title) {
		String discountMessage = "";
		try {
			ArrayList<String> idList = DataBaseManager.matchGameTitle(title);
			for (String id : idList) {
				URL website = new URL(steamDetailUrl + id);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream("temp.json");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				// download app detail json file

				String temp = "";
				BufferedReader mbr = new BufferedReader(
						new InputStreamReader(new FileInputStream("temp.json"), "UTF-8"));
				while (mbr.ready())
					temp += mbr.readLine();
				mbr.close();
				// read app detail json file

				// start parse json
				JSONObject jsonData = new JSONObject(temp);
				System.out.println(jsonData.getJSONObject(id));
				if (jsonData.isNull(id)) continue;
				if (jsonData.getJSONObject(id).isNull("data")) continue;
				jsonData = jsonData.getJSONObject(id).getJSONObject("data");
				if (jsonData.isNull("price_overview")) continue;
				int discountRate = jsonData.getJSONObject("price_overview").getInt("discount_percent");
				// skip no discount item
				
				if (discountRate == 0)
					continue;
				String gameTitle = jsonData.getString("name");

				discountMessage += discountRate + "%;" + gameTitle + ";" + steamGameLinkBase + id + "\n";
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		if (discountMessage.isEmpty())
			return null;

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
		// DataBaseManager db = new DataBaseManager();
		// SteamDeal s = new SteamDeal(db);
		// s.getSalePage();
		// s.parse();
		System.out.println(SteamDeal.isGameInDeal("Killing Floor"));
	}
}
