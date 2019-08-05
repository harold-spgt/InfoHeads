package me.harry0198.infoheads.commands.general.conversations.editspecific;

import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.commands.player.EditCommand;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import java.util.List;

import static me.harry0198.infoheads.InfoHeads.getInstance;

public class ChangePrompt extends StringPrompt {

    public String getPromptText(ConversationContext context) {
        return "Type your new line.";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String s) {

        EditCommand.Types editType = getInstance().getCurrentEditType().get(context.getForWhom());
        String uuid = getInstance().typesMapClass.get(context.getForWhom()).getKey();
        String cap = "Infoheads." + uuid + "." + editType.toString().toLowerCase();

        List<String> list = getInstance().getConfig().getStringList(cap);
        int line = (Integer) context.getSessionData("line-no");
        if (line == -1) {
            list.add(s);
        } else {
            try {
                list.set(line, s);
            } catch (IndexOutOfBoundsException error) {
                return Prompt.END_OF_CONVERSATION;
            }
        }
        getInstance().getConfig().set(cap, list);
        getInstance().saveConfig();

        getInstance().register(InfoHeads.Registerables.INFOHEADS);

        return Prompt.END_OF_CONVERSATION;
    }
}
