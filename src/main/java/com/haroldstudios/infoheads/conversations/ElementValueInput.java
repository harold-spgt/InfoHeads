package com.haroldstudios.infoheads.conversations;

import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.elements.*;
import com.haroldstudios.infoheads.gui.WizardGui;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public final class ElementValueInput extends StringPrompt {

    private final InfoHeadConfiguration configuration;
    private final ElementType element;

    public ElementValueInput(final InfoHeadConfiguration configuration, final ElementType element) {
        this.configuration = configuration;
        this.element = element;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return MessageUtil.INPUT_CONVERSATION;
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {

        InfoHeadConfiguration infoHead = InfoHeads.getInstance().getDataStore().getMatchingInfoHead(configuration);
        if (infoHead == null) {
            return Prompt.END_OF_CONVERSATION;
        }

        switch (element) {
            case ConsoleCommand:
                infoHead.addElement(new ConsoleCommandElement(input));
                break;

            case Delay:
                int val;
                try {
                    val = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    return new ElementValueInput(configuration, element);
                }
                configuration.addElement(new DelayElement(val));
                break;

            case Message:
                infoHead.addElement(new MessageElement(input));
                break;

            case PlayerCommand:
                infoHead.addElement(new PlayerCommandElement(input));
                break;

            case Permission:
                infoHead.setPermission(input);
                break;
        }

        // Schedules the reopening of the editor menu
        WizardGui.scheduleOpen(configuration, (Player) context.getForWhom());

        return Prompt.END_OF_CONVERSATION;
    }
}