package com.Server;

import com.BotModule.BotCommand;
import com.BotModule.BotFries;
import com.Library.LibraryIO;
import static com.Library.LibraryUtil.log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;
import sx.blah.discord.util.audio.AudioPlayer;

public class BotUtils {
	static String TOKEN;
	static IDiscordClient mClient;
	static BotFries Penut = new BotFries();
	static String ID;
	static String BOT_PREFIX = "/";

	public static void main(String[] args) throws DiscordException, RateLimitException {
		TOKEN = LibraryIO.readFile("Data\\Token.dat");
		log("[Server] Logging bot in...\n");
		mClient = new ClientBuilder().withToken(TOKEN).build();
		mClient.getDispatcher().registerListener(new BotUtils());
		mClient.login();
		ID = "<@!" + mClient.getApplicationClientID() + ">";
		log("[Server] Bot ID: " + ID + "\n");
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) {
		log("[Server] Bot is now ready!\n");
	}

	public static void sendMessage(IChannel ch, String msg) {
		ch.sendMessage(msg);
	}

	public static void joinvoice(MessageReceivedEvent event, List<String> args) {
		IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();

		if (userVoiceChannel == null)
			return;

		userVoiceChannel.join();

	}

	public static void leavevoice(MessageReceivedEvent event, List<String> args) {
		IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild())
				.getChannel();

		if (botVoiceChannel == null)
			return;

		botVoiceChannel.leave();
	}

	public static void playsong(MessageReceivedEvent event, List<String> args) {
		IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild())
				.getChannel();

		if (botVoiceChannel == null) {
			BotUtils.sendMessage(event.getChannel(), "Not in a voice channel, join one and then use joinvoice");
			return;
		}

		// Turn the args back into a string separated by space
		String searchStr = String.join(" ", args);

		// Get the AudioPlayer object for the guild
		AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(event.getGuild());
		audioP.setVolume((float) 0.1);

		// Find a song given the search term
		File[] songDir = new File("E:\\Music\\Album\\Idol Master Cinderella Girls")
				.listFiles(file -> file.getName().contains(searchStr));

		if (songDir == null || songDir.length == 0)
			return;

		// Stop the playing track
		audioP.clear();

		// Play the found song
		try {
			audioP.queue(songDir[0]);
		} catch (IOException | UnsupportedAudioFileException e) {
			BotUtils.sendMessage(event.getChannel(), "There was an issue playing that song.");
			e.printStackTrace();
		}

		BotUtils.sendMessage(event.getChannel(), "Now playing: " + songDir[0].getName());
	}

	public static void play(MessageReceivedEvent event) {
		IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild())
				.getChannel();

		if (botVoiceChannel == null) {
			BotUtils.sendMessage(event.getChannel(), "Not in a voice channel, join one and then use joinvoice");
			return;
		}

		// Get the AudioPlayer object for the guild
		AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(event.getGuild());
		audioP.setVolume((float) 0.1);
		audioP.setLoop(true);
		

		// Find a song given the search term
		File[] songDir = new File("E:\\Music\\Album\\Idol Master Cinderella Girls").listFiles();

		if (songDir == null || songDir.length == 0)
			return;

		// Stop the playing track
		audioP.clear();

		// Play the found song
		try {
			for (File f : songDir)
				audioP.queue(f);
		} catch (IOException | UnsupportedAudioFileException e) {
			BotUtils.sendMessage(event.getChannel(), "There was an issue playing that song.");
			e.printStackTrace();
		}
		audioP.shuffle();
	}

	@EventSubscriber
	public void onMessageReceived(MessageReceivedEvent event) {

		String[] argArray = event.getMessage().getContent().split(" ");

		if (argArray.length == 0)
			return;

		if (!argArray[0].startsWith(BotUtils.BOT_PREFIX))
			return;

		String commandStr = argArray[0].substring(1);

		List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
		argsList.remove(0); // Remove the command

		if (commandStr.equals("joinvoice"))
			joinvoice(event, argsList);
		if (commandStr.equals("leavevoice"))
			leavevoice(event, argsList);
		if (commandStr.equals("playsong"))
			playsong(event, argsList);
		
		if (commandStr.equals("play"))
			play(event);

	}
}