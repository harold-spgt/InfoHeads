package me.harry0198.infoheads.spigot.conversations;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.ConsoleCommandElement;
import me.harry0198.infoheads.core.elements.MessageElement;
import me.harry0198.infoheads.core.elements.PlayerCommandElement;
import me.harry0198.infoheads.core.elements.PlayerPermissionElement;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.OpenMenuMenuEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.spigot.model.BukkitOnlinePlayer;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ElementValueInput extends StringPrompt {

    private final InfoHeadProperties configuration;
    private final InputTypes element;
    private final LocalizedMessageService localizedMessageService;
    private final EventDispatcher eventDispatcher;

    public ElementValueInput(EventDispatcher eventDispatcher, InfoHeadProperties configuration, InputTypes element, LocalizedMessageService localizedMessageService) {
        this.localizedMessageService = localizedMessageService;
        this.configuration = configuration;
        this.element = element;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return switch (element) {
            case PERMISSION -> localizedMessageService.getMessage(BundleMessages.REQUEST_PERMISSION);
            case PLAYER_PERMISSION -> localizedMessageService.getMessage(BundleMessages.REQUEST_PLAYER_PERMISSION);
            case RENAME -> localizedMessageService.getMessage(BundleMessages.REQUEST_RENAME);
            case CONSOLE_COMMAND -> localizedMessageService.getMessage(BundleMessages.REQUEST_CONSOLE_COMMAND);
            case PLAYER_COMMAND -> localizedMessageService.getMessage(BundleMessages.REQUEST_PLAYER_COMMAND);
            case MESSAGE -> localizedMessageService.getMessage(BundleMessages.REQUEST_MESSAGE);
            case DELAY -> "";
        };
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

        eventDispatcher.dispatchEvent(new OpenMenuMenuEvent(configuration, new BukkitOnlinePlayer((Player) context.getForWhom())));

        return END_OF_CONVERSATION;
    }
}