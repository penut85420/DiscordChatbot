package com.Server;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Music {

    public static void joinvoice(MessageReceivedEvent event, List<String> args) {
    	IVoiceChannel userVoiceChannel = event.getAuthor().getVoiceStateForGuild(event.getGuild()).getChannel();

        if(userVoiceChannel == null)
            return;

        userVoiceChannel.join();

    }
    
    public static void leavevoice(MessageReceivedEvent event, List<String> args) {
    	 IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild()).getChannel();

         if(botVoiceChannel == null)
             return;

         botVoiceChannel.leave();
    }
    
    public static void playsong(MessageReceivedEvent event, List<String> args) {
    	IVoiceChannel botVoiceChannel = event.getClient().getOurUser().getVoiceStateForGuild(event.getGuild()).getChannel();

        if(botVoiceChannel == null) {
            BotUtils.sendMessage(event.getChannel(), "Not in a voice channel, join one and then use joinvoice");
            return;
        }

        // Turn the args back into a string separated by space
        String searchStr = String.join(" ", args);

        // Get the AudioPlayer object for the guild
        AudioPlayer audioP = AudioPlayer.getAudioPlayerForGuild(event.getGuild());

        // Find a song given the search term
        File[] songDir = new File("E:\\Music\\Album\\Idol Master Cinderella Girls")
                .listFiles(file -> file.getName().contains(searchStr));

        if(songDir == null || songDir.length == 0)
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

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){

        String[] argArray = event.getMessage().getContent().split(" ");

        if(argArray.length == 0)
            return;

        if(!argArray[0].startsWith(BotUtils.BOT_PREFIX))
            return;

        String commandStr = argArray[0].substring(1);

        List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
        argsList.remove(0); // Remove the command

        if (commandStr.equals("joinvoice")) joinvoice(event, argsList);
        if (commandStr.equals("leavevoice")) joinvoice(event, argsList);
        if (commandStr.equals("playsong")) joinvoice(event, argsList);

    }

}