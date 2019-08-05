package me.harry0198.infoheads.commands.general.conversations.editspecific;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;

public class LineSelectPrompt extends NumericPrompt {

    public String getPromptText(ConversationContext context) {
        return "Choose a line number. Type '-1' to add a new line.";
    }

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {

        context.setSessionData("line-no", input);
        return new ChangePrompt();
    }
}
