package me.harry0198.infoheads.commands.general.conversations.wizard;

import me.harry0198.infoheads.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;

public class InfoHeadsConversationPrefix implements ConversationPrefix {
	
	public String getPrefix(ConversationContext context) {
        return Utils.PREFIX + ChatColor.GREEN + "[Wizard] " + ChatColor.WHITE;
    }

}
