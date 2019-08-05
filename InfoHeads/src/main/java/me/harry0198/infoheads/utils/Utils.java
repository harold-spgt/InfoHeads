package me.harry0198.infoheads.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

	public static String PREFIX = "§aInfoHeads: §f";
	
	/**
	 * Send Message to player
	 * 
	 * @param player
	 * @param message
	 */
	
	public static void sendMessage(Player player, String message) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + message));
	}

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + message));
	}

	public static void sendMessage(Player player, String message, boolean prefix) {
		if (prefix)
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + message));
		else
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

}
