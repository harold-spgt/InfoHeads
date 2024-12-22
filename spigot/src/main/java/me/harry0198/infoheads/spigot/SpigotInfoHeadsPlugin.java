package me.harry0198.infoheads.spigot;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.event.InfoHeadEventListenerRegister;
import me.harry0198.infoheads.core.service.ConfigurationService;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;
import me.harry0198.infoheads.spigot.commands.BukkitCmdExecutor;
import me.harry0198.infoheads.spigot.listener.BukkitEventListener;
import me.harry0198.infoheads.spigot.ui.InventoryGuiListener;
import me.harry0198.infoheads.spigot.util.UpdateChecker;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SpigotInfoHeadsPlugin extends InfoHeadsPlugin {

    private final Logger logger = LoggerFactory.getLogger();
    private final JavaPlugin plugin;
    private final BukkitEventListener bukkitEventListener;
    private final EventDispatcher eventDispatcher;
    private final MessageService messageService;
    @Inject
    @SuppressWarnings({"squid:S107"})
    public SpigotInfoHeadsPlugin(
            JavaPlugin plugin,
            MessageService messageService,
            ConfigurationService configurationService,
            InfoHeadService infoHeadService,
            CommandHandler commandHandler,
            BukkitEventListener bukkitEventListener,
            EventDispatcher eventDispatcher,
            InfoHeadEventListenerRegister infoHeadEventListenerRegister) {
        super(configurationService, infoHeadService, commandHandler, infoHeadEventListenerRegister);
        this.plugin = plugin;
        this.bukkitEventListener = bukkitEventListener;
        this.eventDispatcher = eventDispatcher;
        this.messageService = messageService;
    }

    @Override
    public void registerEventHandlers() {
        plugin.getServer().getPluginManager().registerEvents(new InventoryGuiListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(bukkitEventListener, plugin);
    }

    @Override
    public void registerCommandExecutors(CommandHandler commandHandler) {
        PluginCommand command = Objects.requireNonNull(plugin.getCommand("infoheads"));
        command.setExecutor(new BukkitCmdExecutor(commandHandler));
        command.setTabCompleter((completer, cmd, s, args) -> commandHandler.getTabCompletions(BukkitCmdExecutor.parseCommand(args)));
    }


    @Override
    public void unregisterAll() {
        HandlerList.unregisterAll(plugin);
        eventDispatcher.unregisterAll();
    }

    @Override
    public void runUpdateNotifier() {
        (new UpdateChecker(67080)).getVersion(version -> {
            if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info(messageService.getMessage(BundleMessages.UPDATE_AVAILABLE) + " " + version);
            }
        });
    }

    @Override
    public void reload() {
        // Hard reload plugin, dependencies will be reset and plugin started from fresh.
        EntryPoint.getInstance().hardReload();
    }
}
