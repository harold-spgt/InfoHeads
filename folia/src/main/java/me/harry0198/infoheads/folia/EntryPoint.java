package me.harry0198.infoheads.folia;

import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.utils.logging.Level;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;
import me.harry0198.infoheads.spigot.util.BukkitLogger;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class EntryPoint extends JavaPlugin {

    private final InfoHeadsPlugin infoHeadsPlugin;

    public EntryPoint() {
        new Metrics(this, 4607);
        LoggerFactory.setLogger(new BukkitLogger(this, Level.INFO));
        this.infoHeadsPlugin = new FoliaInfoHeadsPlugin(this, getDataFolder().toPath());
    }

    @Override
    public void onEnable() {
        this.infoHeadsPlugin.onEnable();
    }

    @Override
    public void onDisable() {
        this.infoHeadsPlugin.onDisable();
    }
}
