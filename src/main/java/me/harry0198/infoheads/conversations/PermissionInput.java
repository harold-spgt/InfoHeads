package me.harry0198.infoheads.conversations;

import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public final class PermissionInput extends StringPrompt {

    private final InfoHeads plugin;

    public PermissionInput(final InfoHeads plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return MessageUtil.PERMISSION_SET_CONVERSATION;
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        plugin.getDataStore().getDraft((Player) context.getForWhom()).setPermission(input);
        return Prompt.END_OF_CONVERSATION;
    }
}
