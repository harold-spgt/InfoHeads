package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.elements.PlayerPermissionElement;
import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.actions.RemoveTempPlayerPermissionEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        System.out.println(permissionsData.get(event.getPlayer().getUid()));
        System.out.println(event.getPermission());
        PermissionAttachment permissionAttachment = permissionsData.get(event.getPlayer().getUid());
        if (permissionAttachment != null) {
            permissionAttachment.unsetPermission(event.getPermission());
        }
    }
}
