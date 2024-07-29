package me.harry0198.infoheads.folia;

import me.harry0198.infoheads.spigot.SpigotInfoHeadsPlugin;
import me.harry0198.infoheads.spigot.util.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class FoliaInfoHeadsPlugin extends SpigotInfoHeadsPlugin {

    public FoliaInfoHeadsPlugin(JavaPlugin plugin, Path workingDirectory) {
        super(plugin, workingDirectory);
    }

    @Override
    public Scheduler getScheduler() {
        return new FoliaScheduler();
    }
}
