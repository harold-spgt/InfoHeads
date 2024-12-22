package me.harry0198.infoheads.spigot.conversations;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;
import org.jetbrains.annotations.NotNull;

public final class InfoHeadsConversationPrefix implements ConversationPrefix {

    private final MessageService messageService;

    public InfoHeadsConversationPrefix(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public @NotNull String getPrefix(@NotNull ConversationContext context) {
        return messageService.getMessage(BundleMessages.PREFIX);
    }
}
