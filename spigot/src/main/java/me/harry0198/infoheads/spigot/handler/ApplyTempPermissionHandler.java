package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.dispatcher.EventListener;
import me.harry0198.infoheads.core.event.types.ApplyTempPlayerPermissionEvent;
import me.harry0198.infoheads.spigot.EntryPoint;
import me.harry0198.infoheads.spigot.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event handler for {@link ApplyTempPlayerPermissionEvent}.
 * Adds the provided permission {@link ApplyTempPlayerPermissionEvent#getPermission()} when handled.
 */
public class ApplyTempPermissionHandler implements EventListener<ApplyTempPlayerPermissionEvent> {

    private final Scheduler scheduler;
    private final ConcurrentHashMap<UUID, PermissionAttachment> permissionsData;

    public ApplyTempPermissionHandler(Scheduler scheduler, ConcurrentHashMap<UUID, PermissionAttachment> permissionsData) {
        this.permissionsData = permissionsData;
        this.scheduler = scheduler;
    }

    @Override
    public void onEvent(ApplyTempPlayerPermissionEvent event) {
        scheduler.scheduleEntity(event.getOnlinePlayer(), (player) -> {
            PermissionAttachment attachment;

            if (permissionsData.get(player.getUniqueId()) == null) {
                PermissionAttachment permissionAttachment = player.addAttachment(EntryPoint.getInstance());
                permissionsData.put(player.getUniqueId(), permissionAttachment);
            }

            attachment = permissionsData.get(player.getUniqueId());
            attachment.setPermission(event.getPermission(), true);
        });
    }
}
