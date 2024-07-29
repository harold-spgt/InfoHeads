package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.dispatcher.EventListener;
import me.harry0198.infoheads.core.event.types.RemoveTempPlayerPermissionEvent;
import me.harry0198.infoheads.spigot.util.Scheduler;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event handler for {@link RemoveTempPlayerPermissionEvent}.
 * Removes the provided permission {@link RemoveTempPlayerPermissionEvent#getPermission()} when handled.
 */
public class RemoveTempPermissionHandler implements EventListener<RemoveTempPlayerPermissionEvent> {

    private final Scheduler scheduler;
    private final ConcurrentHashMap<UUID, PermissionAttachment> permissionsData;

    public RemoveTempPermissionHandler(Scheduler scheduler, ConcurrentHashMap<UUID, PermissionAttachment> permissionsData) {
        this.permissionsData = permissionsData;
        this.scheduler = scheduler;
    }

    @Override
    public void onEvent(RemoveTempPlayerPermissionEvent event) {
        // Removes Permissions
        scheduler.scheduleEntity(event.getPlayer(), (player) -> {
            PermissionAttachment permissionAttachment = permissionsData.get(event.getPlayer().getUid());
            if (permissionAttachment != null) {
                permissionAttachment.unsetPermission(event.getPermission());
            }
        });
    }
}
