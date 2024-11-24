package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.dispatcher.EventListener;
import me.harry0198.infoheads.core.event.types.RemoveTempPlayerPermissionEvent;
import me.harry0198.infoheads.spigot.EntryPoint;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Event handler for {@link RemoveTempPlayerPermissionEvent}.
 * Removes the provided permission {@link RemoveTempPlayerPermissionEvent#getPermission()} when handled.
 */
public class RemoveTempPermissionHandler implements EventListener<RemoveTempPlayerPermissionEvent> {

    private final ConcurrentMap<UUID, PermissionAttachment> permissionsData;

    public RemoveTempPermissionHandler(ConcurrentMap<UUID, PermissionAttachment> permissionsData) {
        this.permissionsData = permissionsData;
    }

    @Override
    public void onEvent(RemoveTempPlayerPermissionEvent event) {
        // Removes Permissions
        Bukkit.getScheduler().runTask(EntryPoint.getInstance(), () -> {
            PermissionAttachment permissionAttachment = permissionsData.get(event.getPlayer().getUid());
            if (permissionAttachment != null) {
                permissionAttachment.unsetPermission(event.getPermission());
            }
        });
    }
}
