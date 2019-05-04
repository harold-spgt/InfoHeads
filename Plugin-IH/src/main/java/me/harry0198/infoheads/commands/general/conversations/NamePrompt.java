package me.harry0198.infoheads.commands.general.conversations;

import org.apache.commons.lang.StringUtils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import me.harry0198.infoheads.InfoHeads;

public class NamePrompt extends StringPrompt {
	
	protected InfoHeads b;

	public NamePrompt(InfoHeads b) {
		this.b = b;

	}

    public String getPromptText(ConversationContext context) {

        return "Pick a unique, one-word name. Existing: ("  + StringUtils.join(b.infoheads,',') + ")";
    }

	@Override
	public Prompt acceptInput(ConversationContext context, String s) {
		b.name.put((Player) context.getForWhom(), s);
        context.setSessionData("name", s);
        return new CommandPrompt(b);
	}
}
