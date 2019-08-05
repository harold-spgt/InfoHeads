package me.harry0198.infoheads.commands.general.conversations.wizard;

import me.harry0198.infoheads.utils.Utils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandPrompt extends StringPrompt {
	
	public String getPromptText(ConversationContext context) {
		return "Type the next line for your commands (without /). Once ready to move on, type '-'.";
    }

	@Override
	public Prompt acceptInput(ConversationContext context, String s) {

		List<String> currentSession = (List<String>) context.getSessionData("commands");
		if (currentSession == null) currentSession = new ArrayList<>();

		if (s.equals("-")) {
			context.setSessionData("commands", currentSession);
			return new MessagePrompt();
		}

		currentSession.add(s);
		context.setSessionData("commands", currentSession);
		Player player = (Player) context.getForWhom();
		for (String each : currentSession)
			Utils.sendMessage(player, each);
        return new CommandPrompt();
        
	}
}
