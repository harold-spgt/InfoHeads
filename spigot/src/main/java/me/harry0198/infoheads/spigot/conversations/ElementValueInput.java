package me.harry0198.infoheads.spigot.conversations;

import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.*;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.inputs.OpenInfoHeadMenuEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.spigot.model.BukkitOnlinePlayer;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ElementValueInput extends StringPrompt {

    private final InfoHeadProperties configuration;
    private final Element.InfoHeadType element;
    private final LocalizedMessageService localizedMessageService;
    private final InfoHeadService infoHeadService;
    private final EventDispatcher eventDispatcher;

    public ElementValueInput(InfoHeadService infoHeadService, EventDispatcher eventDispatcher, InfoHeadProperties configuration, Element.InfoHeadType element, LocalizedMessageService localizedMessageService) {
        this.infoHeadService = infoHeadService;
        this.localizedMessageService = localizedMessageService;
        this.configuration = configuration;
        this.element = element;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public @NotNull String getPromptText(ConversationContext context) {
        // todo switch for element
        return "Input (change per element later)";
    }

    @Override
    public Prompt acceptInput(@NotNull ConversationContext context, String input) {
        if (configuration == null) {
            return END_OF_CONVERSATION;
        }

        switch (element) {
            case CONSOLE_COMMAND:
                configuration.addElement(new ConsoleCommandElement(input));
                break;

            case DELAY:
                int val;
                try {
                    val = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    return new ElementValueInput(infoHeadService, eventDispatcher, configuration, element, localizedMessageService);
                }
                //TODO
//                configuration.addElement(new DelayElement(val));
                break;

            case MESSAGE:
                configuration.addElement(new MessageElement(input));
                break;

            case PLAYER_COMMAND:
                configuration.addElement(new PlayerCommandElement(input));
                break;

            case PERMISSION:
                configuration.setPermission(input);
                break;
            case PLAYER_PERMISSION:
                configuration.addElement(new PlayerPermissionElement(input));
                break;
            case RENAME:
                configuration.setName(input);
                break;
        }

        eventDispatcher.dispatchEvent(new OpenInfoHeadMenuEvent(configuration, new BukkitOnlinePlayer((Player) context.getForWhom())));

        return END_OF_CONVERSATION;
    }
}