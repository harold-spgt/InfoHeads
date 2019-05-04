package me.harry0198.infoheads;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface VersionInterface {
    boolean triggerOnce(PlayerInteractEvent event);
    void items(Player player);
}
