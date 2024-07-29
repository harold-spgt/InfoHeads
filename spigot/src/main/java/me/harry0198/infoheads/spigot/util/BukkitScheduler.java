package me.harry0198.infoheads.spigot.util;

import me.harry0198.infoheads.spigot.EntryPoint;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class BukkitScheduler implements Scheduler {


    @Override
    public void schedule(Runnable runnable) {
        Bukkit.getScheduler().runTask(EntryPoint.getInstance(), runnable);
    }

    @Override
    public void scheduleEntity(me.harry0198.infoheads.core.model.Player p, Consumer<Player> runnable) {
        Player player = Bukkit.getPlayer(p.getUid());
        if (player == null || !player.isOnline()) {
            return;
        }
        Bukkit.getScheduler().runTask(EntryPoint.getInstance(), () -> runnable.accept(player));
    }

    @Override
    public void scheduleEntity(Player player, Runnable runnable) {
        Bukkit.getScheduler().runTask(EntryPoint.getInstance(), runnable);
    }
}
