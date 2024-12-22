package me.harry0198.infoheads.spigot;

import com.google.inject.Guice;
import me.harry0198.infoheads.core.Plugin;
import me.harry0198.infoheads.core.di.CoreModule;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.logging.Level;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;
import me.harry0198.infoheads.legacy.Converter;
import me.harry0198.infoheads.spigot.di.SpigotModule;
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

    private Plugin infoHeadsPlugin;
    private InfoHeadService infoHeadService;

    public EntryPoint() {
        // We set level to debug here because during the initialization procedure,
        // this flag is overridden immediately by configuration options. However, if this cannot be loaded then an error
        // has occurred and we want to be able to see why at the debug level.
        LoggerFactory.setLogger(new BukkitLogger(Level.DEBUG));

        initialize();
    }

    /**
     * Resets the stored instances of infoheads.
     */
    public void hardReload() {
        onDisable();
        initialize();
        infoHeadsPlugin.onEnable();
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

    private void initialize() {
        var injector = Guice.createInjector(new CoreModule(), new SpigotModule(this));
        injector.injectMembers(this);

        this.infoHeadsPlugin = injector.getInstance(SpigotInfoHeadsPlugin.class);
        this.infoHeadService = injector.getInstance(InfoHeadService.class);
    }

    private void convertLegacyDataStore() {
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
                List<CompletableFuture<Boolean>> futures = infoHeadProperties.stream().map(infoHeadService::addInfoHead).toList();
                CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
                all.thenRun(() -> {
                    Path renameDataStore = Paths.get(this.getDataFolder().getAbsolutePath(), "datastore-old.json");
                    if (!legacyDataStore.toFile().renameTo(renameDataStore.toFile())) {
                        getLogger().warning("Could not rename legacy data store file to new.");
                    }
                });

            } catch (IOException e) {
                getLogger().severe("Unable to perform legacy data store conversion. Cannot bring legacy to current version.");
                return;
            }

            Bukkit.getScheduler().runTaskLater(this, infoHeadsPlugin::reload, 20L);
        }
    }
}
