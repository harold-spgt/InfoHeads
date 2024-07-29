package me.harry0198.infoheads.folia;

import me.harry0198.infoheads.spigot.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class FoliaScheduler implements Scheduler {

    @Override
    public void schedule(Runnable runnable) {
        Bukkit.getGlobalRegionScheduler().run(EntryPoint.getInstance(), scheduledTask -> runnable.run());
    }

    @Override
    public void scheduleEntity(me.harry0198.infoheads.core.model.Player p, Consumer<Player> consumer) {
        Player player = Bukkit.getPlayer(p.getUid());
        if (player == null || !player.isOnline()) {
            return;
        }
        player.getScheduler().run(EntryPoint.getInstance(), scheduledTask -> consumer.accept(player), () -> {});
    }

    @Override
    public void scheduleEntity(Player player, Runnable runnable) {
        player.getScheduler().run(EntryPoint.getInstance(), scheduledTask -> runnable.run(), () -> {});
    }
}
