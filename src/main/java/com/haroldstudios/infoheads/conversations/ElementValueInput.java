package com.haroldstudios.infoheads.conversations;

import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.elements.*;
import com.haroldstudios.infoheads.gui.WizardGui;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ElementValueInput extends StringPrompt {

    private final InfoHeadConfiguration configuration;
    private final DataStore dataStore;
    private final Element.InfoHeadType element;

    public ElementValueInput(DataStore dataStore, InfoHeadConfiguration configuration, Element.InfoHeadType element) {
        this.dataStore = dataStore;
        this.configuration = configuration;
        this.element = element;
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return MessageUtil.getString(MessageUtil.Message.INPUT_CONVERSATION);
    }

    @Override
    public Prompt acceptInput(@NotNull ConversationContext context, String input) {

        InfoHeadConfiguration infoHead = dataStore.getMatchingInfoHead(configuration);
        if (infoHead == null) {
            return Prompt.END_OF_CONVERSATION;
        }

        switch (element) {
            case CONSOLE_COMMAND:
                infoHead.addElement(new ConsoleCommandElement(input));
                break;

            case DELAY:
                int val;
                try {
                    val = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    return new ElementValueInput(dataStore, configuration, element);
                }
                configuration.addElement(new DelayElement(val));
                break;

            case MESSAGE:
                infoHead.addElement(new MessageElement(input));
                break;

            case PLAYER_COMMAND:
                infoHead.addElement(new PlayerCommandElement(input));
                break;

            case PERMISSION:
                infoHead.setPermission(input);
                break;
            case PLAYER_PERMISSION:
                infoHead.addElement(new PlayerPermissionElement(input));
                break;
        }

        // Schedules the reopening of the editor menu
        WizardGui.scheduleOpen(configuration, (Player) context.getForWhom());

        return Prompt.END_OF_CONVERSATION;
    }
}