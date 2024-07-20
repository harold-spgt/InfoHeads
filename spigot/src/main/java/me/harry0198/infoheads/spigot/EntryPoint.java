package me.harry0198.infoheads.spigot;

import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.utils.logging.Level;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;
import me.harry0198.infoheads.legacy.Converter;
import me.harry0198.infoheads.spigot.util.BukkitLogger;
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
        LoggerFactory.setLogger(new BukkitLogger(Level.INFO));
        this.infoHeadsPlugin = new SpigotInfoHeadsPlugin(this, getDataFolder().toPath());
    }

    @Override
    public void onEnable() {
//        new Metrics(this, 4607);
        infoHeadsPlugin.onEnable();

        if (convertLegacyDataStore()) {
            infoHeadsPlugin.reload();
        }
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
            return true;
        }
        return false;
    }
}
