package me.harry0198.infoheads.spigot;

import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.utils.logging.Level;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;
import me.harry0198.infoheads.legacy.Converter;
import me.harry0198.infoheads.spigot.util.BukkitLogger;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Entrypoint for plugin.
 * Makes calls to the InfoHeadsPlugin for enable & disable.
 * Also includes some spigot-specific initializations here such as the convert from legacy datastore.
 */
public final class EntryPoint extends JavaPlugin {

    private final InfoHeadsPlugin infoHeadsPlugin;

    public EntryPoint() {
        LoggerFactory.setLogger(new BukkitLogger(Level.DEBUG));
        this.infoHeadsPlugin = new SpigotInfoHeadsPlugin(this, getDataFolder().toPath());
    }

    @Override
    public void onEnable() {
        new Metrics(this, 4607);
        infoHeadsPlugin.onEnable();

        convertLegacyDataStore();
    }

    @Override
    public void onDisable() {
        infoHeadsPlugin.onDisable();
    }

    public static EntryPoint getInstance() {
        return getPlugin(EntryPoint.class);
    }

    private boolean convertLegacyDataStore() {
        Path legacyDataStore = Paths.get(this.getDataFolder().getAbsolutePath(), "datastore.json");
        if (legacyDataStore.toFile().exists()) {
            getLogger().info("PERFORMING DATA STORAGE AND CONFIG.YML CONVERSION. SOME WARNINGS MAY APPEAR. YOU MAY NEED TO RESTART IF ISSUES OCCUR. IF YOU " +
                    "HAVE ANY ISSUES, PLEASE CONTACT THE DEVELOPER. OR, ALTERNATIVELY CONTINUE USING \"LEGACY\" INFOHEADS (BELOW v2.5.0)");
            Path configFile = Paths.get(this.getDataFolder().getAbsolutePath(), "config.yml");
            try {
                Files.delete(configFile);
            } catch (IOException e) {
                getLogger().warning("An old plugin version config.yml was detected and could not be replaced. Please manually delete the config.yml file and restart.");
            }

            Converter converter = new Converter();
            try {
                List<InfoHeadProperties> infoHeadProperties = converter.convert(Files.readString(legacyDataStore));
                List<CompletableFuture<Boolean>> futures = infoHeadProperties.stream().map(x -> infoHeadsPlugin.getInfoHeadService().addInfoHead(x)).toList();
                CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
                all.thenRun(() -> {
                    Path renameDataStore = Paths.get(this.getDataFolder().getAbsolutePath(), "datastore-old.json");
                    legacyDataStore.toFile().renameTo(renameDataStore.toFile());
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Bukkit.getScheduler().runTaskLater(this, infoHeadsPlugin::reload, 20L);
            return true;
        }
        return false;
    }
}
