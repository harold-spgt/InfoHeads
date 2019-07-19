package me.harry0198.infoheads.commands.general.conversations;

import me.harry0198.infoheads.utils.Utils;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MessagePrompt extends StringPrompt {

    public String getPromptText(ConversationContext context) {
        return "Type the next line of your message. Once ready to move on, type '-'.";
    }

	@Override
	public Prompt acceptInput(ConversationContext context, String s) {
		List<String> currentSession = (List<String>) context.getSessionData("messages");
		if (currentSession == null) currentSession = new ArrayList<>();

		if (s.equals("-")) {
			context.setSessionData("messages", currentSession);
			return new ExecutedPrompt();
		}

		currentSession.add(s);
		context.setSessionData("messages", currentSession);
		Player player = (Player) context.getForWhom();
		for (String each : currentSession)
			Utils.sendMessage(player, each);
		return new MessagePrompt();
	}
}
