package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.dispatcher.EventListener;
import me.harry0198.infoheads.core.event.types.ApplyTempPlayerPermissionEvent;
import me.harry0198.infoheads.spigot.EntryPoint;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Event handler for {@link ApplyTempPlayerPermissionEvent}.
 * Adds the provided permission {@link ApplyTempPlayerPermissionEvent#getPermission()} when handled.
 */
public class ApplyTempPermissionHandler implements EventListener<ApplyTempPlayerPermissionEvent> {

    private final ConcurrentMap<UUID, PermissionAttachment> permissionsData;

    public ApplyTempPermissionHandler(ConcurrentMap<UUID, PermissionAttachment> permissionsData) {
        this.permissionsData = permissionsData;
    }

    @Override
    public void onEvent(ApplyTempPlayerPermissionEvent event) {
        Bukkit.getScheduler().runTask(EntryPoint.getInstance(), () -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
                if (player != null && player.isOnline()) {
                    PermissionAttachment attachment;

                    if (permissionsData.get(player.getUniqueId()) == null) {
                        PermissionAttachment permissionAttachment = player.addAttachment(EntryPoint.getInstance());
                        permissionsData.put(player.getUniqueId(), permissionAttachment);
                    }

                    attachment = permissionsData.get(player.getUniqueId());
                    attachment.setPermission(event.getPermission(), true);
                }
        });
    }
}
