package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.actions.RemoveTempPlayerPermissionEvent;
import me.harry0198.infoheads.spigot.InfoHeads;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event handler for {@link RemoveTempPlayerPermissionEvent}.
 * Removes the provided permission {@link RemoveTempPlayerPermissionEvent#getPermission()} when handled.
 */
public class RemoveTempPermissionHandler implements EventListener<RemoveTempPlayerPermissionEvent> {

    private final ConcurrentHashMap<UUID, PermissionAttachment> permissionsData;

    public RemoveTempPermissionHandler(ConcurrentHashMap<UUID, PermissionAttachment> permissionsData) {
        this.permissionsData = permissionsData;
    }

    @Override
    public void onEvent(RemoveTempPlayerPermissionEvent event) {
        // Removes Permissions
        Bukkit.getScheduler().runTask(InfoHeads.getInstance(), () -> {
            PermissionAttachment permissionAttachment = permissionsData.get(event.getPlayer().getUid());
            if (permissionAttachment != null) {
                permissionAttachment.unsetPermission(event.getPermission());
            }
        });
    }
}
