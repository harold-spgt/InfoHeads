package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.actions.ApplyTempPlayerPermissionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Event handler for {@link ApplyTempPlayerPermissionEvent}.
 * Adds the provided permission {@link ApplyTempPlayerPermissionEvent#getPermission()} when handled.
 */
public class ApplyTempPermissionHandler implements EventListener<ApplyTempPlayerPermissionEvent> {
    @Override
    public void onEvent(ApplyTempPlayerPermissionEvent event) {
        Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
        if (player != null && player.isOnline()) {
            //TODO
        }
    }
}
