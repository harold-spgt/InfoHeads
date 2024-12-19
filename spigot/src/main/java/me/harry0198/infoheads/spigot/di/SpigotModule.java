package me.harry0198.infoheads.spigot.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.service.ConfigurationService;
import me.harry0198.infoheads.core.service.LocalizedMessageService;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.di.CoreModule;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.handlers.*;
import me.harry0198.infoheads.core.hooks.PlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.hooks.VanillaPlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.Constants;
import me.harry0198.infoheads.spigot.EntryPoint;
import me.harry0198.infoheads.spigot.SpigotInfoHeadsPlugin;
import me.harry0198.infoheads.spigot.hooks.PAPIPlaceholderHandlingStrategy;
import me.harry0198.infoheads.spigot.listener.BukkitEventListener;
import me.harry0198.infoheads.spigot.listener.InfoHeadEventHandlerRegister;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;

public class SpigotModule extends AbstractModule {

    private final EntryPoint entryPoint;

    public SpigotModule(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Override
    protected void configure() {
        bind(InfoHeadsPlugin.class).to(SpigotInfoHeadsPlugin.class);
//        bind(MyService.class).to(MyServiceImpl.class);
    }

    @Provides
    JavaPlugin provideJavaPlugin() {
        return this.entryPoint;
    }

    @Provides
    @CoreModule.WorkingDirectory
    Path provideWorkingDirectory() {
        return this.entryPoint.getDataFolder().toPath();
    }

    @Provides
    UnaryOperator<String> provideColourReplaceStrategy() {
        return str -> {
            str = ChatColor.translateAlternateColorCodes('&', str);
            str = translateHexColorCodes(str);

            return str;
        };
    }

    @Provides
    BukkitEventListener provideBukkitEventListener(BreakHandler breakHandler, InteractHandler interactHandler, PlaceHandler placeHandler, PlayerJoinHandler playerJoinHandler, PlayerQuitHandler playerQuitHandler, InfoHeadEventHandlerRegister register) {
            return new BukkitEventListener(
                breakHandler,
                interactHandler,
                placeHandler,
                playerJoinHandler,
                playerQuitHandler,
                register.getPermissionsMapping()
        );
    }

    @Provides
    static PlaceholderHandlingStrategy providePlaceholderHandlingStrategy() {
        if (packagesExists("me.clip.placeholderapi.PlaceholderAPI")) {
//            logger.info("Hook - PlaceholderAPI integration successful.");
            return new PAPIPlaceholderHandlingStrategy();
        } else {
            return new VanillaPlaceholderHandlingStrategy();
        }
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
