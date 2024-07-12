package me.harry0198.infoheads.spigot;


import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.handlers.*;
import me.harry0198.infoheads.core.hooks.PlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.hooks.VanillaPlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.persistence.repository.Repository;
import me.harry0198.infoheads.core.persistence.repository.RepositoryFactory;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.service.UserStateService;
import me.harry0198.infoheads.core.utils.Constants;
import me.harry0198.infoheads.spigot.hooks.PAPIPlaceholderHandlingStrategy;
import me.harry0198.infoheads.spigot.util.HexUtils;
import me.harry0198.infoheads.spigot.util.UpdateChecker;
import me.harry0198.infoheads.spigot.commands.BukkitCmdExecutor;
import me.harry0198.infoheads.spigot.listener.BukkitEventListener;
import me.harry0198.infoheads.spigot.listener.InfoHeadEventHandlerRegister;
import me.harry0198.infoheads.spigot.ui.InventoryGuiListener;

import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

/**
 * Entrypoint for plugin.
 * Initializes strategies and handlers for the core to use.
 * Initializes and registers handlers and executors to bukkit.
 */
public final class InfoHeads extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger(InfoHeads.class.getName());
    private final PlaceholderHandlingStrategy placeholderHandlingStrategy;
    private final LocalizedMessageService localizedMessageService;
    private final EventDispatcher eventDispatcher;
    private final UserStateService userStateService;
    private final InfoHeadService infoHeadService;

    public InfoHeads() {
        this.placeholderHandlingStrategy = choosePlaceholderStrategy();
        this.localizedMessageService = generateLocalizedMessageService();
        this.eventDispatcher = EventDispatcher.getInstance();
        this.userStateService = new UserStateService();

        Repository<InfoHeadProperties> repository = RepositoryFactory.getRepository();
        this.infoHeadService = new InfoHeadService(repository);
    }

    @Override
    public void onEnable() {
//        new Metrics(this, 4607);

        // Set executor.
        CommandHandler commandHandler = new CommandHandler(infoHeadService, userStateService, localizedMessageService, eventDispatcher);
        PluginCommand command = Objects.requireNonNull(this.getCommand("infoheads"));
        command.setExecutor(new BukkitCmdExecutor(commandHandler));
        command.setTabCompleter((completer, cmd, s, args) -> commandHandler.getTabCompletions(BukkitCmdExecutor.parseCommand(args)));

        // Register handlers.
        InfoHeadEventHandlerRegister infoHeadEventHandlerRegister = new InfoHeadEventHandlerRegister(infoHeadService, localizedMessageService);
        getServer().getPluginManager().registerEvents(new InventoryGuiListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitEventListener(
                new BreakHandler(infoHeadService, localizedMessageService, eventDispatcher),
                new InteractHandler(infoHeadService, localizedMessageService, placeholderHandlingStrategy, eventDispatcher),
                new PlaceHandler(infoHeadService, userStateService, localizedMessageService, eventDispatcher),
                new PlayerJoinHandler(eventDispatcher),
                new PlayerQuitHandler(eventDispatcher),
                infoHeadEventHandlerRegister.getPermissionsMapping()
        ), this);

        // Start update checker.
        (new UpdateChecker(67080)).getVersion((version) -> {
            if (InfoHeads.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                LOGGER.info("There is no new update available.");
            } else {
                LOGGER.info("There is a new update available. Version: " + version);
            }
        });
    }

    @Override
    public void onDisable() {
        // Write cache to file and block until complete.
        infoHeadService.saveCacheToRepository().join();
    }

    public static InfoHeads getInstance() {
        return getPlugin(InfoHeads.class);
    }

    private LocalizedMessageService generateLocalizedMessageService() {
        Locale locale = Locale.forLanguageTag("en-GB");
        UnaryOperator<String> colourReplaceStrategy = (str) -> {
            str = ChatColor.translateAlternateColorCodes('&', str);
            if (HexUtils.supportsHex()) {
                str = HexUtils.translateHexColorCodes(str, Constants.HEX_PATTERN);
            }
            return str;
        };

        return new LocalizedMessageService(locale, colourReplaceStrategy);
    }

    private PlaceholderHandlingStrategy choosePlaceholderStrategy() {
        if (packagesExists("me.clip.placeholderapi.PlaceholderAPI")) {
            LOGGER.info("Hook - PlaceholderAPI integration successful.");
            return new PAPIPlaceholderHandlingStrategy();
        } else {
            return new VanillaPlaceholderHandlingStrategy();
        }
    }

    /**
     * Determines if all packages in a String array are within the Classpath
     * This is the best way to determine if a specific plugin exists and will be
     * loaded. If the plugin package isn't loaded, we shouldn't bother waiting
     * for it!
     * @param packages String Array of package names to check
     * @return Success or Failure
     */
    private static boolean packagesExists(String...packages) {
        try {
            for (String pkg : packages) {
                Class.forName(pkg);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
