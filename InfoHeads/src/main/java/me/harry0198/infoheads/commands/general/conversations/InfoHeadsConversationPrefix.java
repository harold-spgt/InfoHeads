package me.harry0198.infoheads.commands.general.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;

public class InfoHeadsConversationPrefix implements ConversationPrefix {
	
	public String getPrefix(ConversationContext context) {
        return ChatColor.GREEN + "InfoHeads Wizard: " + ChatColor.WHITE;
    }

}
