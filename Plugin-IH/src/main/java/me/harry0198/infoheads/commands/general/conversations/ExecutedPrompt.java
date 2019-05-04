package me.harry0198.infoheads.commands.general.conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import me.harry0198.infoheads.InfoHeads;

public class ExecutedPrompt extends MessagePrompt {
	
	protected InfoHeads b;

	public ExecutedPrompt(InfoHeads b) {
		this.b = b;

	}

	public String getPromptText(ConversationContext context) {
		String name = (String) context.getSessionData("name");
		String command = (String) context.getSessionData("command");
		String message = (String) context.getSessionData("message");
		
		b.namedComplete.add((Player) context.getForWhom());
		b.getConfig().set("# InfoHead: " + name, name);
		b.getConfig().set(name + ".name", name);
		b.getConfig().set(name + ".message", message);
		b.getConfig().set(name + ".command", command);
		b.saveConfig();

		return "Now you may place your infohead / desired block!";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) {
		return Prompt.END_OF_CONVERSATION;
	}

}
