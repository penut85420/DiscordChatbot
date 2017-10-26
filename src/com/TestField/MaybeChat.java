package com.TestField;

import com.BotModule.BotCommand;
import com.BotModule.BotPenut;
import com.Library.LibraryIO;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

public class MaybeChat {
	static String TOKEN;
	static IDiscordClient client;
	static BotPenut Penut = new BotPenut();
	
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
		String msg = message.getContent();
		if (user.isBot()) return;
		
		IChannel channel = message.getChannel();
		
		if (cmdCheck(msg, channel)) return;

		String[] returnMsg = Penut.sendMessage(msg);
		
		for (String s: returnMsg)
			channel.sendMessage(s);
	}
	
	private boolean cmdCheck(String msg, IChannel ch) {
		if (BotCommand.isCmd(msg)) {
			boolean isLegalCmd = BotCommand.doCmd(msg);
			if (!isLegalCmd)
				ch.sendMessage("Not a legal command.");
			return true;
		}
		
		return false;
	}
	
	private static void log(Object obj) {
		System.out.println(obj.toString());
	}

}