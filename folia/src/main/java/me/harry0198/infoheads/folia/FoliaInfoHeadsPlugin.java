package me.harry0198.infoheads.folia;

import me.harry0198.infoheads.spigot.SpigotInfoHeadsPlugin;
import me.harry0198.infoheads.spigot.util.Scheduler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class FoliaInfoHeadsPlugin extends SpigotInfoHeadsPlugin {

    private final Plugin plugin;

    public FoliaInfoHeadsPlugin(JavaPlugin plugin, Path workingDirectory) {
        super(plugin, workingDirectory);
        this.plugin = plugin;
    }

    @Override
    public Scheduler getScheduler() {
        return new FoliaScheduler(plugin);
    }
}
