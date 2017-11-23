package com.Server;

import com.BotModule.BotCommand;
import com.BotModule.BotFries;
import com.Library.LibraryIO;
import static com.Library.LibraryUtil.log;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

public class MainChat {
	static String TOKEN;
	static IDiscordClient client;
	static BotFries Penut = new BotFries();
	
	public static void main(String[] args) throws DiscordException, RateLimitException {
		TOKEN = LibraryIO.readFile("data/token.dat");
		log("[Server] Logging bot in...\n");
		client = new ClientBuilder().withToken(TOKEN).build();
		client.getDispatcher().registerListener(new MainChat());
		client.login();
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) {
		log("[Server] Bot is now ready!\n");
	}

	@SuppressWarnings("deprecation")
	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event)  {
		IMessage message = event.getMessage();
		IUser user = message.getAuthor();
		log("[Server] Author: " + user.getName() + ";" + user.getAvatar() + ";" + user.getDisplayName(event.getGuild()) + "\n");
		String msg = message.getContent();
		if (user.isBot()) return;
		
		IChannel channel = message.getChannel();
		
		if (cmdCheck(msg, channel)) return;
		
		for (String s: Penut.sendMessage(msg))
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
}