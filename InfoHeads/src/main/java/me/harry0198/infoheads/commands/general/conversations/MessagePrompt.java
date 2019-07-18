package me.harry0198.infoheads.commands.general.conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import me.harry0198.infoheads.InfoHeads;

public class MessagePrompt extends StringPrompt {
	
	protected InfoHeads b;

	public MessagePrompt(InfoHeads b) {
		this.b = b;

	}

    public String getPromptText(ConversationContext context) {
        return "What message would you like it to say? If nothing, type '-'";
    }

	@Override
	public Prompt acceptInput(ConversationContext context, String s) {
		if (s.equals("-")) {
			context.setSessionData("message", "");
			return new ExecutedPrompt(b);
		}
		context.setSessionData("message", s);
        return new ExecutedPrompt(b);
	}
}
