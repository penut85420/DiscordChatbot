package com.test;

import com.BotModule.BotPenut;
import com.Library.LibraryIO;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

public class MaybeChat {
	private static String TOKEN;
	private static IDiscordClient client;

	public static void main(String[] args) throws DiscordException, RateLimitException {
		TOKEN = LibraryIO.readFile("data/token.dat");
		log("Logging bot in...");
		client = new ClientBuilder().withToken(TOKEN).build();
		client.getDispatcher().registerListener(new MaybeChat());
		client.login();
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) {
		log("Bot is now ready!");
	}

	@SuppressWarnings("deprecation")
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event)  {
		IMessage message = event.getMessage();
		IUser user = message.getAuthor();
		if (user.isBot()) return;
		
		IChannel channel = message.getChannel();
//		IGuild guild = message.getGuild();
		// String[] returnMsg = new BotBear().response(message.getContent());
		String[] returnMsg = BotPenut.response(message.getContent());
		System.out.println(message.getContent());
		for (String s: returnMsg) {
			channel.sendMessage(s);
		}
	}
	
	private static void log(Object obj) {
		System.out.println(obj.toString());
	}

}