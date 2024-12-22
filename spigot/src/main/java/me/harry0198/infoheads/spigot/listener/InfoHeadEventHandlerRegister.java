package me.harry0198.infoheads.spigot.listener;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.event.InfoHeadEventListenerRegister;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class InfoHeadEventHandlerRegister implements InfoHeadEventListenerRegister {

    private final EventDispatcher eventDispatcher;
    private final InfoHeadService infoHeadService;
    private final MessageService messageService;
    private final SendPlayerMessageHandler sendPlayerMessageHandler;
    private final RemoveTempPermissionHandler removeTempPermissionHandler;
    private final SendPlayerCommandHandler sendPlayerCommandHandler;
    private final SendConsoleCommandHandler sendConsoleCommandHandler;
    private final ApplyTempPermissionHandler applyTempPermissionHandler;
    private final JavaPlugin javaPlugin;

    @Inject
    @SuppressWarnings({"squid:S107"})
    public InfoHeadEventHandlerRegister(
            JavaPlugin javaPlugin,
            InfoHeadService infoHeadService,
            EventDispatcher eventDispatcher,
            MessageService messageService,
            SendPlayerMessageHandler sendPlayerMessageHandler,
            RemoveTempPermissionHandler removeTempPermissionHandler,
            SendPlayerCommandHandler sendPlayerCommandHandler,
            SendConsoleCommandHandler sendConsoleCommandHandler,
            ApplyTempPermissionHandler applyTempPermissionHandler) {
        this.javaPlugin = javaPlugin;
        this.eventDispatcher = Objects.requireNonNull(eventDispatcher);
        this.messageService = Objects.requireNonNull(messageService);
        this.infoHeadService = Objects.requireNonNull(infoHeadService);
        this.sendPlayerMessageHandler = Objects.requireNonNull(sendPlayerMessageHandler);
        this.removeTempPermissionHandler = Objects.requireNonNull(removeTempPermissionHandler);
        this.sendPlayerCommandHandler = Objects.requireNonNull(sendPlayerCommandHandler);
        this.sendConsoleCommandHandler = Objects.requireNonNull(sendConsoleCommandHandler);
        this.applyTempPermissionHandler = Objects.requireNonNull(applyTempPermissionHandler);
    }

    @Override
    public void registerSendPlayerMessageListener() {
        eventDispatcher.registerListener(SendPlayerMessageEvent.class, Objects.requireNonNull(sendPlayerMessageHandler));
    }

    @Override
    public void registerRemoveTempPermissionListener() {
        eventDispatcher.registerListener(RemoveTempPlayerPermissionEvent.class, Objects.requireNonNull(removeTempPermissionHandler));
    }

    @Override
    public void registerSendPlayerCommandListener() {
        eventDispatcher.registerListener(SendPlayerCommandEvent.class, Objects.requireNonNull(sendPlayerCommandHandler));
    }

    @Override
    public void registerSendConsoleCommandListener() {
        eventDispatcher.registerListener(SendConsoleCommandEvent.class, Objects.requireNonNull(sendConsoleCommandHandler));
    }

    @Override
    public void registerApplyTempPlayerPermissionListener() {
        eventDispatcher.registerListener(ApplyTempPlayerPermissionEvent.class, Objects.requireNonNull(applyTempPermissionHandler));
    }

    @Override
    public void registerOpenMenuListener() {
        eventDispatcher.registerListener(OpenMenuMenuEvent.class, event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        javaPlugin,
                        () -> new EditInfoHeadGui(new EditInfoHeadViewModel(eventDispatcher, infoHeadService, event.getInfoHeadProperties(), messageService), messageService).open(player));
            }
        });
    }

    @Override
    public void registerOpenAddActionMenuListener() {
        eventDispatcher.registerListener(OpenAddActionMenuEvent.class, event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        javaPlugin,
                        () -> new AddActionGui(new AddActionViewModel(eventDispatcher, event.getInfoHeadProperties()), messageService).open(player));
            }
        });
    }

    @Override
    public void registerGetConsoleCommandInputListener() {
        eventDispatcher.registerListener(GetConsoleCommandInputEvent.class, event -> getInputEvent(InputTypes.CONSOLE_COMMAND).accept(event));
    }

    @Override
    public void registerGetPlayerCommandInputListener() {
        eventDispatcher.registerListener(GetPlayerCommandInputEvent.class, event -> getInputEvent(InputTypes.PLAYER_COMMAND).accept(event));
    }

    @Override
    public void registerGetMessageInputListener() {
        eventDispatcher.registerListener(GetMessageInputEvent.class, event -> getInputEvent(InputTypes.MESSAGE).accept(event));
    }

    @Override
    public void registerGetPlayerPermissionInputListener() {
        eventDispatcher.registerListener(GetPlayerPermissionInputEvent.class, event -> getInputEvent(InputTypes.PLAYER_PERMISSION).accept(event));
    }

    @Override
    public void registerGetPermissionInputListener() {
        eventDispatcher.registerListener(GetPermissionInputEvent.class, event -> getInputEvent(InputTypes.PERMISSION).accept(event));
    }

    @Override
    public void registerGetDelayInputListener() {
        eventDispatcher.registerListener(GetDelayInputEvent.class, event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        javaPlugin,
                        () -> new TimePeriodGui(new DelayViewModel(event.getInfoHeadProperties(), eventDispatcher, new TimePeriod(0, 0, 0, 0, 0)), messageService).open(player));
            }
        });
    }

    @Override
    public void registerGetCoolDownInputListener() {
        eventDispatcher.registerListener(GetCoolDownInputEvent.class, event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                Bukkit.getScheduler().runTask(
                        EntryPoint.getInstance(),
                        () -> new TimePeriodGui(new CoolDownViewModel(event.getInfoHeadProperties(), eventDispatcher), messageService).open(player));
            }
        });
    }

    @Override
    public void registerGetNameInputListener() {
        eventDispatcher.registerListener(GetNameInputEvent.class, event -> getInputEvent(InputTypes.RENAME).accept(event));
    }

    @Override
    @SuppressWarnings({"squid:S1874", "deprecation"})
    public void registerShowInfoHeadListListener() {
        eventDispatcher.registerListener(ShowInfoHeadListEvent.class, event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player == null || !player.isOnline()) {
                return;
            }

            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(event.getOnlinePlayer(), messageService.getMessage(BundleMessages.LIST_CMD_HEADER)));
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(event.getOnlinePlayer(), messageService.getMessage(BundleMessages.LIST_CMD_EXPLAIN)));

            for (InfoHeadProperties infoHeadProperties : event.getInfoHeadPropertiesList()) {
                me.harry0198.infoheads.core.model.Location loc = infoHeadProperties.getLocation();
                String locString = String.format("§b%s %s %s", loc.x(), loc.y(), loc.z());
                String name = infoHeadProperties.getName() != null ? "§8" + infoHeadProperties.getName() + " §7- " + locString : locString;

                TextComponent component = new TextComponent(name);
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + loc.x() + " " + loc.y() + " " + loc.z()));
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bClick to teleport").create()));

                player.spigot().sendMessage(component);
            }
        });
    }

    @Override
    public void registerGivePlayerHeadsListener() {
        eventDispatcher.registerListener(GivePlayerHeadsEvent.class, event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player == null || !player.isOnline()) {
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
        });
    }

    private Consumer<InputEvent> getInputEvent(InputTypes infoHeadType) {
        return event -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
            if (player != null && player.isOnline()) {
                player.sendTitle(messageService.getMessage(BundleMessages.CONVERSATION_TITLE), messageService.getMessage(BundleMessages.CONVERSATION_SUBTITLE), 10, 40, 10);
                getInputFactory(event.getInfoHeadProperties(), infoHeadType, messageService)
                        .buildConversation(player).begin();
            }
        };
    }

    private ConversationFactory getInputFactory(final InfoHeadProperties infoHeadConfiguration, final InputTypes element, MessageService messageService) {
        return new ConversationFactory(EntryPoint.getInstance())
                .withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix(messageService))
                .withFirstPrompt(new ElementValueInput(eventDispatcher, infoHeadConfiguration, element, messageService))
                .withEscapeSequence("cancel")
                .withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
    }
}
