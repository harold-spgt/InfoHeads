package me.harry0198.infoheads.conversations;

import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.elements.DelayElement;
import me.harry0198.infoheads.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public final class DelayInput extends NumericPrompt {

    private final InfoHeads plugin;

    public DelayInput(final InfoHeads plugin) {
        this.plugin = plugin;
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        plugin.getDataStore().getDraft((Player) context.getForWhom()).addElement(new DelayElement(input.intValue()));

        return Prompt.END_OF_CONVERSATION;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return MessageUtil.DELAY_INPUT;
    }
}
