package me.harry0198.infoheads.commands.general.conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import me.harry0198.infoheads.InfoHeads;

public class CommandPrompt extends StringPrompt {
	
	protected InfoHeads b;

	public CommandPrompt(InfoHeads b) {
		this.b = b;

	}
	
	public String getPromptText(ConversationContext context) {
		// Doesn't actually matter what is typed besides 'cancel'
        return "What command would you like this to run? If nothing, type '-'. PlaceHolders in Config.yml";
    }

	@Override
	public Prompt acceptInput(ConversationContext context, String s) {
		if (s.equals("-")) {
			context.setSessionData("command", "");
			return new MessagePrompt(b);
		}
		context.setSessionData("command", s);
        return new MessagePrompt(b);
        
	}
}
