package me.harry0198.infoheads.spigot.listener;

import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.InputEvent;
import me.harry0198.infoheads.core.event.actions.*;
import me.harry0198.infoheads.core.event.inputs.*;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.ui.EditInfoHeadViewModel;
import me.harry0198.infoheads.core.ui.TimePeriodViewModel;
import me.harry0198.infoheads.spigot.InfoHeads;
import me.harry0198.infoheads.spigot.conversations.ElementValueInput;
import me.harry0198.infoheads.spigot.conversations.InfoHeadsConversationPrefix;
import me.harry0198.infoheads.spigot.handler.*;
import me.harry0198.infoheads.spigot.model.BukkitOnlinePlayer;
import me.harry0198.infoheads.spigot.ui.cooldown.TimePeriodGui;
import me.harry0198.infoheads.spigot.ui.edit.EditInfoHeadGui;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class InfoHeadEventHandlerRegister {

    private final EventDispatcher eventDispatcher;
    private final LocalizedMessageService localizedMessageService;
    private final InfoHeadService infoHeadService;

    public InfoHeadEventHandlerRegister(InfoHeadService infoHeadService, LocalizedMessageService localizedMessageService) {
        this.eventDispatcher = EventDispatcher.getInstance();
        this.infoHeadService = infoHeadService;
        this.localizedMessageService = localizedMessageService;

        eventDispatcher.registerListener(SendPlayerMessageEvent.class, new SendPlayerMessageHandler());
        eventDispatcher.registerListener(RemoveTempPlayerPermissionEvent.class, new RemoveTempPermissionHandler());
        eventDispatcher.registerListener(SendPlayerCommandEvent.class, new SendPlayerCommandHandler());
        eventDispatcher.registerListener(SendConsoleCommandEvent.class, new SendConsoleCommandHandler());
        eventDispatcher.registerListener(ApplyTempPlayerPermissionEvent.class, new ApplyTempPermissionHandler());
        eventDispatcher.registerListener(OpenInfoHeadMenuEvent.class, openInfoHeadMenu());
        eventDispatcher.registerListener(GetConsoleCommandInputEvent.class, getConsoleCommandInputEventListener());
        eventDispatcher.registerListener(GetPlayerCommandInputEvent.class, getPlayerCommandInputEventListener());
        eventDispatcher.registerListener(GetMessageInputEvent.class, getMessageInputEventListener());
        eventDispatcher.registerListener(GetPlayerPermissionInputEvent.class, getPlayerPermissionInputEventListener());
        eventDispatcher.registerListener(GetPermissionInputEvent.class, getPermissionInputEventListener());
        eventDispatcher.registerListener(GetDelayInputEvent.class, getDelayInputEventListener());
        eventDispatcher.registerListener(GetCoolDownInputEvent.class, getCooldownInputEventListener());
    }

    private EventListener<GetCoolDownInputEvent> getCooldownInputEventListener() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        InfoHeads.getInstance(),
                        () -> new TimePeriodGui(new TimePeriodViewModel(event.getInfoHeadProperties()), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<GetDelayInputEvent> getDelayInputEventListener() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        InfoHeads.getInstance(),
                        () -> new TimePeriodGui(new TimePeriodViewModel(event.getInfoHeadProperties()), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<OpenInfoHeadMenuEvent> openInfoHeadMenu() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        InfoHeads.getInstance(),
                        () -> new EditInfoHeadGui(new EditInfoHeadViewModel(eventDispatcher, event.getInfoHeadProperties()), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<GetConsoleCommandInputEvent> getConsoleCommandInputEventListener() {
        return event -> getInputEvent(Element.InfoHeadType.CONSOLE_COMMAND).accept(event);
    }

    private EventListener<GetPlayerCommandInputEvent> getPlayerCommandInputEventListener() {
        return event -> getInputEvent(Element.InfoHeadType.PLAYER_COMMAND).accept(event);
    }

    private EventListener<GetMessageInputEvent> getMessageInputEventListener() {
        return event -> getInputEvent(Element.InfoHeadType.MESSAGE).accept(event);
    }

    private EventListener<GetPlayerPermissionInputEvent> getPlayerPermissionInputEventListener() {
        return event -> getInputEvent(Element.InfoHeadType.PLAYER_PERMISSION).accept(event);
    }

    private EventListener<GetPermissionInputEvent> getPermissionInputEventListener() {
        return event -> getInputEvent(Element.InfoHeadType.PERMISSION).accept(event);
    }

    private Consumer<InputEvent> getInputEvent(Element.InfoHeadType infoHeadType) {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                getInputFactory(event.getInfoHeadProperties(), infoHeadType, infoHeadService, localizedMessageService)
                        .buildConversation(player).begin();
            }
        };
    }

    private static ConversationFactory getInputFactory(final InfoHeadProperties infoHeadConfiguration, final Element.InfoHeadType element, InfoHeadService infoHeadService, LocalizedMessageService localizedMessageService) {
        return new ConversationFactory(InfoHeads.getInstance())
                .withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix(localizedMessageService))
                .withFirstPrompt(new ElementValueInput(infoHeadService, EventDispatcher.getInstance(), infoHeadConfiguration, element, localizedMessageService))
                .withEscapeSequence("cancel")
                .withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
    }
}
