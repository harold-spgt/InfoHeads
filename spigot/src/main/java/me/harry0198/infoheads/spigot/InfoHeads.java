package me.harry0198.infoheads.spigot;


import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.handlers.*;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.persistence.repository.Repository;
import me.harry0198.infoheads.core.persistence.repository.RepositoryFactory;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.service.UserStateService;
import me.harry0198.infoheads.core.utils.Constants;
import me.harry0198.infoheads.spigot.util.HexUtils;
import me.harry0198.infoheads.spigot.util.UpdateChecker;
import me.harry0198.infoheads.spigot.commands.BukkitCmdExecutor;
import me.harry0198.infoheads.spigot.listener.BukkitEventListener;
import me.harry0198.infoheads.spigot.listener.InfoHeadEventHandlerRegister;
import me.harry0198.infoheads.spigot.ui.InventoryGuiListener;

import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

public final class InfoHeads extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger(InfoHeads.class.getName());

    @Override
    public void onEnable() {
        new Metrics(this, 4607);

        Locale locale = Locale.forLanguageTag("en-GB");
        UnaryOperator<String> colourReplaceStrategy = (str) -> {
            str = ChatColor.translateAlternateColorCodes('&', str);
            if (HexUtils.supportsHex()) {
                str = HexUtils.translateHexColorCodes(str, Constants.HEX_PATTERN);
            }
            return str;
        };

        LocalizedMessageService localizedMessageService = new LocalizedMessageService(locale, colourReplaceStrategy);

        Repository<InfoHeadProperties> repository = RepositoryFactory.getRepository();
        EventDispatcher eventDispatcher = EventDispatcher.getInstance();

        InfoHeadService infoHeadService = new InfoHeadService(repository);
        UserStateService userStateService = new UserStateService();
        CommandHandler commandHandler = new CommandHandler(infoHeadService, userStateService, localizedMessageService, eventDispatcher);


        // Register event listeners.
        InfoHeadEventHandlerRegister infoHeadEventHandlerRegister = new InfoHeadEventHandlerRegister(infoHeadService, localizedMessageService);

        Objects.requireNonNull(this.getCommand("infoheads")).setExecutor(new BukkitCmdExecutor(commandHandler));

        getServer().getPluginManager().registerEvents(new InventoryGuiListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitEventListener(
                new BreakHandler(infoHeadService, localizedMessageService, eventDispatcher),
                new InteractHandler(infoHeadService, localizedMessageService, eventDispatcher),
                new PlaceHandler(infoHeadService, userStateService, localizedMessageService, eventDispatcher),
                new PlayerJoinHandler(eventDispatcher),
                new PlayerQuitHandler(eventDispatcher),
                infoHeadEventHandlerRegister.getPermissionsMapping()
        ), this);

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
        // Close down
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

    public static InfoHeads getInstance() {
        return getPlugin(InfoHeads.class);
    }
}
