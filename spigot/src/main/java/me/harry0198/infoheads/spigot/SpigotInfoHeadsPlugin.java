package me.harry0198.infoheads.spigot;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.config.BundleMessages;
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

public class SpigotInfoHeadsPlugin implements InfoHeadsPlugin {

    private final Logger logger = LoggerFactory.getLogger();
    private final JavaPlugin plugin;
    private final BukkitEventListener bukkitEventListener;
    private final EventDispatcher eventDispatcher;
    private final MessageService messageService;
    private final InfoHeadService infoHeadService;
    private final ConfigurationService configurationService;
    private final CommandHandler commandHandler;

    @Inject
    public SpigotInfoHeadsPlugin(JavaPlugin plugin, MessageService messageService, ConfigurationService configurationService, InfoHeadService infoHeadService, CommandHandler commandHandler, BukkitEventListener bukkitEventListener, EventDispatcher eventDispatcher) {
        this.plugin = plugin;
        this.bukkitEventListener = bukkitEventListener;
        this.eventDispatcher = eventDispatcher;
        this.messageService = messageService;
        this.infoHeadService = infoHeadService;
        this.configurationService = configurationService;
        this.commandHandler = commandHandler;
    }

    @Override
    public void registerEventHandlers() {
        plugin.getServer().getPluginManager().registerEvents(new InventoryGuiListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(bukkitEventListener, plugin);

        logger.info("Spigot");
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
            if (!EntryPoint.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info(messageService.getMessage(BundleMessages.UPDATE_AVAILABLE) + " " + version);
            }
        });
    }

    @Override
    public InfoHeadService getInfoHeadService() {
        return null;
    }

    @Override
    public void onEnable() {
        load();

        logger.info("Hi");
        var configurationOptional = this.configurationService.getConfiguration();
        if (configurationOptional.isPresent()) {
            if (configurationOptional.get().isCheckForUpdate()) {
                runUpdateNotifier();
            }
        } else {
            runUpdateNotifier();
        }

    }

    @Override
    public void onDisable() {
        // Write cache to file and block until complete.
        infoHeadService.saveCacheToRepository().join();
    }

    @Override
    public void reload() {
        onDisable();
        unregisterAll();
        load();
    }

    @Override
    public void load() {
        // Reloading / Loading is a synchronous task, we could have mismatched DI if we do this off thread... so join.
        this.configurationService.getConfigInitializationProcedure().join();
        this.configurationService.getConfiguration().ifPresent(config -> LoggerFactory.getLogger().setLevel(config.getLoggingLevel()));

        registerCommandExecutors(commandHandler);

        registerEventHandlers();
    }
}
