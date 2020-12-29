package com.haroldstudios.infoheads.conversations;

import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;
import org.jetbrains.annotations.NotNull;

public final class InfoHeadsConversationPrefix implements ConversationPrefix {

    public String getPrefix(@NotNull ConversationContext context) {
        return MessageUtil.PREFIX + ChatColor.WHITE;
    }
}
