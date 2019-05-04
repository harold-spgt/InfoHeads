package me.harry0198.infoheads.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
	
	/**
	 * Send Message to player
	 * 
	 * @param player
	 * @param message
	 */
	
	public static void sendMessage(Player player, String message) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
}
