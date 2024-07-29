package me.harry0198.infoheads.folia;


import me.harry0198.infoheads.core.InfoHeadsPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class EntryPoint extends JavaPlugin {

    private final InfoHeadsPlugin infoHeadsPlugin;

    public EntryPoint() {
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

    public static EntryPoint getInstance() {
        return getPlugin(EntryPoint.class);
    }
}
