package me.harry0198.infoheads.spigot.conversations;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;
import org.jetbrains.annotations.NotNull;

public final class InfoHeadsConversationPrefix implements ConversationPrefix {

    private final LocalizedMessageService localizedMessageService;

    public InfoHeadsConversationPrefix(LocalizedMessageService localizedMessageService) {
        this.localizedMessageService = localizedMessageService;
    }

    @Override
    public @NotNull String getPrefix(@NotNull ConversationContext context) {
        return localizedMessageService.getMessage(BundleMessages.PREFIX);
    }
}
