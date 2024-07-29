package me.harry0198.infoheads.spigot.util;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface Scheduler {

    void schedule(Runnable runnable);

    void scheduleEntity(me.harry0198.infoheads.core.model.Player player, Consumer<Player> runnable);

    void scheduleEntity(Player player, Runnable runnable);
}
