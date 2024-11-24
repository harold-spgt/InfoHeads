package me.harry0198.infoheads.spigot.listener;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.dispatcher.EventListener;
import me.harry0198.infoheads.core.event.types.*;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.ui.AddActionViewModel;
import me.harry0198.infoheads.core.ui.CoolDownViewModel;
import me.harry0198.infoheads.core.ui.DelayViewModel;
import me.harry0198.infoheads.core.ui.EditInfoHeadViewModel;
import me.harry0198.infoheads.spigot.EntryPoint;
import me.harry0198.infoheads.spigot.conversations.ElementValueInput;
import me.harry0198.infoheads.spigot.conversations.InfoHeadsConversationPrefix;
import me.harry0198.infoheads.spigot.conversations.InputTypes;
import me.harry0198.infoheads.spigot.handler.*;
import me.harry0198.infoheads.spigot.ui.builder.ItemBuilder;
import me.harry0198.infoheads.spigot.ui.cooldown.TimePeriodGui;
import me.harry0198.infoheads.spigot.ui.edit.EditInfoHeadGui;
import me.harry0198.infoheads.spigot.ui.wizard.AddActionGui;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
        eventDispatcher.registerListener(OpenMenuMenuEvent.class, openInfoHeadMenu());
        eventDispatcher.registerListener(OpenAddActionMenuEvent.class, openAppendInfoHeadMenu());
        eventDispatcher.registerListener(GetConsoleCommandInputEvent.class, getConsoleCommandInputEventListener());
        eventDispatcher.registerListener(GetPlayerCommandInputEvent.class, getPlayerCommandInputEventListener());
        eventDispatcher.registerListener(GetMessageInputEvent.class, getMessageInputEventListener());
        eventDispatcher.registerListener(GetPlayerPermissionInputEvent.class, getPlayerPermissionInputEventListener());
        eventDispatcher.registerListener(GetPermissionInputEvent.class, getPermissionInputEventListener());
        eventDispatcher.registerListener(GetDelayInputEvent.class, getDelayInputEventListener());
        eventDispatcher.registerListener(GetCoolDownInputEvent.class, getCooldownInputEventListener());
        eventDispatcher.registerListener(GetNameInputEvent.class, getNameInputEventListener());
        eventDispatcher.registerListener(ShowInfoHeadListEvent.class, getShowInfoHeadListEvent());
        eventDispatcher.registerListener(GivePlayerHeadsEvent.class, getGivePlayerHeadsEventEventListener());
    }

    public EventListener<GivePlayerHeadsEvent> getGivePlayerHeadsEventEventListener() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player == null && !player.isOnline()) {
                return;
            }

            List<String> textures = List.of(
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM0ZTA2M2NhZmI0NjdhNWM4ZGU0M2VjNzg2MTkzOTlmMzY5ZjRhNTI0MzRkYTgwMTdhOTgzY2RkOTI1MTZhMCJ9fX0=",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBiMDVlNjk5ZDI4YjNhMjc4YTkyZDE2OWRjYTlkNTdjMDc5MWQwNzk5NGQ4MmRlM2Y5ZWQ0YTQ4YWZlMGUxZCJ9fX0=",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDliMjk4M2MwMWI4ZGE3ZGMxYzBmMTJkMDJjNGFiMjBjZDhlNjg3NWU4ZGY2OWVhZTJhODY3YmFlZTYyMzZkNCJ9fX0=",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM5ZWU3MTU0OTc5YjNmODc3MzVhMWM4YWMwODc4MTRiNzkyOGQwNTc2YTI2OTViYTAxZWQ2MTYzMTk0MjA0NSJ9fX0=",
                    "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTkyZTBjYzMyZDg5ODIyMTJiMzVmNDBkOWVhNTNmNzRiNmJmYTdiZGQyMjNmNzViMGMzMjg2MGFmNzM1ZjNkNyJ9fX0="
            );
            for (String texture : textures) {
                ItemStack playerSkull = new ItemBuilder(Material.PLAYER_HEAD)
                        .texture(texture)
                        .build();
                player.getInventory().addItem(playerSkull);
            }
        };
    }

    public ConcurrentMap<UUID, PermissionAttachment> getPermissionsMapping() {
        return permissionsMapping;
    }

    private EventListener<ShowInfoHeadListEvent> getShowInfoHeadListEvent() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player == null || !player.isOnline()) {
                return;
            }

            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(event.getOnlinePlayer(), localizedMessageService.getMessage(BundleMessages.LIST_CMD_HEADER)));
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(event.getOnlinePlayer(), localizedMessageService.getMessage(BundleMessages.LIST_CMD_EXPLAIN)));


            for (InfoHeadProperties infoHeadProperties : event.getInfoHeadPropertiesList()) {
                me.harry0198.infoheads.core.model.Location loc = infoHeadProperties.getLocation();
                String locString = String.format("§b%s %s %s", loc.x(), loc.y(), loc.z());
                String name = infoHeadProperties.getName() != null ? "§8" + infoHeadProperties.getName() + " §7- " + locString : locString;

                TextComponent component = new TextComponent(name);
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + loc.x() + " " + loc.y() + " " + loc.z()));
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bClick to teleport").create()));

                player.spigot().sendMessage(component);
            }
        };
    }

    private EventListener<GetNameInputEvent> getNameInputEventListener() {
        return event -> getInputEvent(InputTypes.RENAME).accept(event);
    }

    private EventListener<GetCoolDownInputEvent> getCooldownInputEventListener() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        EntryPoint.getInstance(),
                        () -> new TimePeriodGui(new CoolDownViewModel(event.getInfoHeadProperties()), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<GetDelayInputEvent> getDelayInputEventListener() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        EntryPoint.getInstance(),
                        () -> new TimePeriodGui(new DelayViewModel(event.getInfoHeadProperties(), new TimePeriod(0,0,0,0,0)), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<OpenMenuMenuEvent> openInfoHeadMenu() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        EntryPoint.getInstance(),
                        () -> new EditInfoHeadGui(new EditInfoHeadViewModel(eventDispatcher, infoHeadService, event.getInfoHeadProperties(), localizedMessageService), localizedMessageService).open(player));
            }
        };
    }

    private EventListener<OpenAddActionMenuEvent> openAppendInfoHeadMenu() {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        EntryPoint.getInstance(),
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
                player.sendTitle(localizedMessageService.getMessage(BundleMessages.CONVERSATION_TITLE), localizedMessageService.getMessage(BundleMessages.CONVERSATION_SUBTITLE),10,40,10);
                getInputFactory(event.getInfoHeadProperties(), infoHeadType, localizedMessageService)
                        .buildConversation(player).begin();
            }
        };
    }

    private static ConversationFactory getInputFactory(final InfoHeadProperties infoHeadConfiguration, final InputTypes element, LocalizedMessageService localizedMessageService) {
        return new ConversationFactory(EntryPoint.getInstance())
                .withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix(localizedMessageService))
                .withFirstPrompt(new ElementValueInput(EventDispatcher.getInstance(), infoHeadConfiguration, element, localizedMessageService))
                .withEscapeSequence("cancel")
                .withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
    }
}
