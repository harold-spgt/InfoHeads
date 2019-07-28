package me.harry0198.infoheads.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {

	public static String PREFIX = "§aInfoHeads: §f";
	private static String HEADER_FOOTER = "§8+§m-------§8[§bIF Help§8]§m-------§8+";
	
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

	/**
	 * Sends the help message to a player
	 *
	 * @param player Player Entity
	 */
	public static void showHelp(Player player) {
		player.sendMessage(helpMessages());
	}

	/**
	 * Sends the help message to the sender
	 *
	 * @param sender CommandSender
	 */
	public static void showHelp(CommandSender sender) {
		sender.sendMessage(helpMessages());
	}

	/**
	 * Help message for the plugin
	 *
	 * @return Array of Strings
	 */
	private static String[] helpMessages() {
		String[] msg = {
				HEADER_FOOTER,
				"§8[§b+§8] §7/infoheads help §8- §7Shows this help menu",
				"§8[§b+§8] §7/infoheads wizard §8- §7Opens up the wizard",
				"§8[§b+§8] §7/infoheads delete §8- §7Deletes an infohead where you're looking",
				"§8[§b+§8] §7/infoheads reload §8- §7Reloads the plugin",
				HEADER_FOOTER
		};
		return msg;
	}

}
