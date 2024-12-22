package me.harry0198.infoheads.spigot.handler;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.event.dispatcher.EventListener;
import me.harry0198.infoheads.core.event.types.RemoveTempPlayerPermissionEvent;
import me.harry0198.infoheads.spigot.di.annotations.PermissionsData;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Event handler for {@link RemoveTempPlayerPermissionEvent}.
 * Removes the provided permission {@link RemoveTempPlayerPermissionEvent#getPermission()} when handled.
 */
public class RemoveTempPermissionHandler implements EventListener<RemoveTempPlayerPermissionEvent> {

    private final ConcurrentMap<UUID, PermissionAttachment> permissionsData;
    private final JavaPlugin javaPlugin;

    @Inject
    public RemoveTempPermissionHandler(@PermissionsData ConcurrentMap<UUID, PermissionAttachment> permissionsData, JavaPlugin javaPlugin) {
        this.permissionsData = Objects.requireNonNull(permissionsData);
        this.javaPlugin = Objects.requireNonNull(javaPlugin);
    }

    @Override
    public void onEvent(RemoveTempPlayerPermissionEvent event) {
        // Removes Permissions
        Bukkit.getScheduler().runTask(javaPlugin, () -> {
            PermissionAttachment permissionAttachment = permissionsData.get(event.getPlayer().getUid());
            if (permissionAttachment != null) {
                permissionAttachment.unsetPermission(event.getPermission());
            }
        });
    }
}
