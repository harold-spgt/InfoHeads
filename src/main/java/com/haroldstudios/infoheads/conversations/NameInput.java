package com.haroldstudios.infoheads.conversations;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.ui.wizard.WizardGui;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NameInput extends StringPrompt {
    private final InfoHeadConfiguration configuration;

    public NameInput(final InfoHeadConfiguration configuration) {
        this.configuration = configuration;
    }

    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context) {
        return MessageUtil.getString(MessageUtil.Message.INPUT_CONVERSATION);
    }

    @Nullable
    @Override
    public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
        InfoHeadConfiguration infoHead = InfoHeads.getInstance().getDataStore().getMatchingInfoHead(configuration);
        if (infoHead == null) {
            return Prompt.END_OF_CONVERSATION;
        }

        infoHead.setName(input);
        WizardGui.scheduleOpen(infoHead, (Player) context.getForWhom());

        return Prompt.END_OF_CONVERSATION;
    }
}
