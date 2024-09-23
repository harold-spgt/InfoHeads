package me.harry0198.infoheads.spigot.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class BukkitScheduler implements Scheduler {

    private final Plugin plugin;

    public BukkitScheduler(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void schedule(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    @Override
    public void scheduleEntity(me.harry0198.infoheads.core.model.Player p, Consumer<Player> runnable) {
        Player player = Bukkit.getPlayer(p.getUid());
        if (player == null || !player.isOnline()) {
            return;
        }
        Bukkit.getScheduler().runTask(plugin, () -> runnable.accept(player));
    }

    @Override
    public void scheduleEntity(Player player, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }
}
