package me.harry0198.infoheads.spigot;

import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.handlers.*;
import me.harry0198.infoheads.core.hooks.PlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.hooks.VanillaPlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.utils.Constants;
import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;
import me.harry0198.infoheads.spigot.commands.BukkitCmdExecutor;
import me.harry0198.infoheads.spigot.hooks.PAPIPlaceholderHandlingStrategy;
import me.harry0198.infoheads.spigot.listener.BukkitEventListener;
import me.harry0198.infoheads.spigot.listener.InfoHeadEventHandlerRegister;
import me.harry0198.infoheads.spigot.ui.InventoryGuiListener;
import me.harry0198.infoheads.spigot.util.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;

public class SpigotInfoHeadsPlugin extends InfoHeadsPlugin {

    private final Logger logger = LoggerFactory.getLogger();
    private final JavaPlugin plugin;

    public SpigotInfoHeadsPlugin(JavaPlugin plugin, Path workingDirectory) {
        super(workingDirectory);
        this.plugin = plugin;
    }

    @Override
    public void registerEventHandlers(BreakHandler breakHandler, InteractHandler interactHandler, PlaceHandler placeHandler, PlayerJoinHandler playerJoinHandler, PlayerQuitHandler playerQuitHandler) {
        InfoHeadEventHandlerRegister infoHeadEventHandlerRegister = new InfoHeadEventHandlerRegister(getInfoHeadService(), getLocalizedMessageService());
        plugin.getServer().getPluginManager().registerEvents(new InventoryGuiListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BukkitEventListener(
                breakHandler,
                interactHandler,
                placeHandler,
                playerJoinHandler,
                playerQuitHandler,
                infoHeadEventHandlerRegister.getPermissionsMapping()
        ), plugin);
    }

    @Override
    public void registerCommandExecutors(CommandHandler commandHandler) {
        PluginCommand command = Objects.requireNonNull(plugin.getCommand("infoheads"));
        command.setExecutor(new BukkitCmdExecutor(commandHandler));
        command.setTabCompleter((completer, cmd, s, args) -> getCommandHandler().getTabCompletions(BukkitCmdExecutor.parseCommand(args)));
    }

    @Override
    public void unregisterAll() {
        HandlerList.unregisterAll(plugin);
        EventDispatcher.getInstance().unregisterAll();
    }

    @Override
    public UnaryOperator<String> getColourReplaceStrategy() {
        return str -> {
            str = ChatColor.translateAlternateColorCodes('&', str);
            str = translateHexColorCodes(str);

            return str;
        };
    }

    @Override
    public PlaceholderHandlingStrategy getPlaceholderHandlingStrategy() {
        if (packagesExists("me.clip.placeholderapi.PlaceholderAPI")) {
            logger.info("Hook - PlaceholderAPI integration successful.");
            return new PAPIPlaceholderHandlingStrategy();
        } else {
            return new VanillaPlaceholderHandlingStrategy();
        }
    }

    @Override
    public void runUpdateNotifier() {
        (new UpdateChecker(67080)).getVersion(version -> {
            if (!EntryPoint.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info(getLocalizedMessageService().getMessage(BundleMessages.UPDATE_AVAILABLE) + " " + version);
            }
        });
    }

    private static String translateHexColorCodes(final String message) {
        final char colorChar = net.md_5.bungee.api.ChatColor.COLOR_CHAR;

        final Matcher matcher = Constants.HEX_PATTERN.matcher(message);
        final StringBuilder buffer = new StringBuilder(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
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
