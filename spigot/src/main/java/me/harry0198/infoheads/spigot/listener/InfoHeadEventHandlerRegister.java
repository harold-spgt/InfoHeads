package me.harry0198.infoheads.spigot.listener;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.InputEvent;
import me.harry0198.infoheads.core.event.actions.*;
import me.harry0198.infoheads.core.event.inputs.*;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.ui.*;
import me.harry0198.infoheads.spigot.InfoHeads;
import me.harry0198.infoheads.spigot.conversations.ElementValueInput;
import me.harry0198.infoheads.spigot.conversations.InfoHeadsConversationPrefix;
import me.harry0198.infoheads.spigot.conversations.InputTypes;
import me.harry0198.infoheads.spigot.handler.*;
import me.harry0198.infoheads.spigot.ui.cooldown.TimePeriodGui;
import me.harry0198.infoheads.spigot.ui.edit.EditInfoHeadGui;
import me.harry0198.infoheads.spigot.ui.wizard.AddActionGui;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class InfoHeadEventHandlerRegister {

    private final EventDispatcher eventDispatcher;
    private final InfoHeadService infoHeadService;
    private final LocalizedMessageService localizedMessageService;
    private final ConcurrentHashMap<UUID, PermissionAttachment> permissionsMapping = new ConcurrentHashMap<>();

    public InfoHeadEventHandlerRegister(InfoHeadService infoHeadService, LocalizedMessageService localizedMessageService) {
        this.eventDispatcher = EventDispatcher.getInstance();
        this.localizedMessageService = localizedMessageService;
        this.infoHeadService = infoHeadService;

        // Register all listeners.
        eventDispatcher.registerListener(SendPlayerMessageEvent.class, new SendPlayerMessageHandler(localizedMessageService.getColourReplaceStrategy()));
        eventDispatcher.registerListener(RemoveTempPlayerPermissionEvent.class, new RemoveTempPermissionHandler(permissionsMapping));
        eventDispatcher.registerListener(SendPlayerCommandEvent.class, new SendPlayerCommandHandler());
        eventDispatcher.registerListener(SendConsoleCommandEvent.class, new SendConsoleCommandHandler());
        eventDispatcher.registerListener(ApplyTempPlayerPermissionEvent.class, new ApplyTempPermissionHandler(permissionsMapping));
        eventDispatcher.registerListener(OpenInfoHeadMenuEvent.class, openInfoHeadMenu());
        eventDispatcher.registerListener(OpenAddActionMenuEvent.class, openAppendInfoHeadMenu());
        eventDispatcher.registerListener(GetConsoleCommandInputEvent.class, getConsoleCommandInputEventListener());
        eventDispatcher.registerListener(GetPlayerCommandInputEvent.class, getPlayerCommandInputEventListener());
        eventDispatcher.registerListener(GetMessageInputEvent.class, getMessageInputEventListener());
        eventDispatcher.registerListener(GetPlayerPermissionInputEvent.class, getPlayerPermissionInputEventListener());
        eventDispatcher.registerListener(GetPermissionInputEvent.class, getPermissionInputEventListener());
        eventDispatcher.registerListener(GetDelayInputEvent.class, getDelayInputEventListener());
        eventDispatcher.registerListener(GetCoolDownInputEvent.class, getCooldownInputEventListener());
        eventDispatcher.registerListener(GetNameInputEvent.class, getNameInputEventListener());
    }

    public ConcurrentHashMap<UUID, PermissionAttachment> getPermissionsMapping() {
        return permissionsMapping;
    }
    

    private EventListener<GetNameInputEvent> getNameInputEventListener() {
        return event -> getInputEvent(InputTypes.RENAME).accept(event);
    }

    private EventListener<GetCoolDownInputEvent> getCooldownInputEventListener() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        InfoHeads.getInstance(),
                        () -> new TimePeriodGui(new CoolDownViewModel(event.getInfoHeadProperties()), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<GetDelayInputEvent> getDelayInputEventListener() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        InfoHeads.getInstance(),
                        () -> new TimePeriodGui(new DelayViewModel(event.getInfoHeadProperties(), new TimePeriod(0,0,0,0,0)), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<OpenInfoHeadMenuEvent> openInfoHeadMenu() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        InfoHeads.getInstance(),
                        () -> new EditInfoHeadGui(new EditInfoHeadViewModel(eventDispatcher, infoHeadService, event.getInfoHeadProperties(), localizedMessageService), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<OpenAddActionMenuEvent> openAppendInfoHeadMenu() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        InfoHeads.getInstance(),
                        () -> new AddActionGui(new AddActionViewModel(eventDispatcher, event.getInfoHeadProperties()), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<GetConsoleCommandInputEvent> getConsoleCommandInputEventListener() {
        return event -> getInputEvent(InputTypes.CONSOLE_COMMAND).accept(event);
    }

    private EventListener<GetPlayerCommandInputEvent> getPlayerCommandInputEventListener() {
        return event -> getInputEvent(InputTypes.PLAYER_COMMAND).accept(event);
    }

    private EventListener<GetMessageInputEvent> getMessageInputEventListener() {
        return event -> getInputEvent(InputTypes.MESSAGE).accept(event);
    }

    private EventListener<GetPlayerPermissionInputEvent> getPlayerPermissionInputEventListener() {
        return event -> getInputEvent(InputTypes.PLAYER_PERMISSION).accept(event);
    }

    private EventListener<GetPermissionInputEvent> getPermissionInputEventListener() {
        return event -> getInputEvent(InputTypes.PERMISSION).accept(event);
    }

    private Consumer<InputEvent> getInputEvent(InputTypes infoHeadType) {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                player.sendTitle(localizedMessageService.getMessage(BundleMessages.CONVERSATION_TITLE), localizedMessageService.getMessage(BundleMessages.CONVERSATION_SUBTITLE),1,5,1);
                getInputFactory(event.getInfoHeadProperties(), infoHeadType, localizedMessageService)
                        .buildConversation(player).begin();
            }
        };
    }

    private static ConversationFactory getInputFactory(final InfoHeadProperties infoHeadConfiguration, final InputTypes element, LocalizedMessageService localizedMessageService) {
        return new ConversationFactory(InfoHeads.getInstance())
                .withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix(localizedMessageService))
                .withFirstPrompt(new ElementValueInput(EventDispatcher.getInstance(), infoHeadConfiguration, element, localizedMessageService))
                .withEscapeSequence("cancel")
                .withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
    }
}
