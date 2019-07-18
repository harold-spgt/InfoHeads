package me.harry0198.infoheads.commands.general.conversations;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import me.harry0198.infoheads.InfoHeads;

import java.util.ArrayList;
import java.util.List;

public class ExecutedPrompt extends MessagePrompt {
	
	protected InfoHeads infoHeads;

	public ExecutedPrompt(InfoHeads infoHeads) {
		this.infoHeads = infoHeads;

	}

	public String getPromptText(ConversationContext context) {
		String command = (String) context.getSessionData("command");
		String message = (String) context.getSessionData("message");

		List<String> msg = new ArrayList<>();
		msg.add(message);

		List<String> cmd = new ArrayList<>();
		cmd.add(command);

		infoHeads.namedComplete.add((Player) context.getForWhom());
		infoHeads.getConfig().set("Infoheads." + (infoHeads.keys + 1) + ".messages", msg);
		infoHeads.getConfig().set("Infoheads." + (infoHeads.keys + 1) + ".commands", cmd);
		infoHeads.saveConfig();

		return "Now you may place your infohead / desired block!";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) {
		return Prompt.END_OF_CONVERSATION;
	}

}
