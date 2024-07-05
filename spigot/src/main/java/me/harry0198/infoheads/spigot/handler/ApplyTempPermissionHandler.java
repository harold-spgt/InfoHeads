package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.actions.ApplyTempPlayerPermissionEvent;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.spigot.InfoHeads;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event handler for {@link ApplyTempPlayerPermissionEvent}.
 * Adds the provided permission {@link ApplyTempPlayerPermissionEvent#getPermission()} when handled.
 */
public class ApplyTempPermissionHandler implements EventListener<ApplyTempPlayerPermissionEvent> {

    private final ConcurrentHashMap<UUID, PermissionAttachment> permissionsData;

    public ApplyTempPermissionHandler(ConcurrentHashMap<UUID, PermissionAttachment> permissionsData) {
        this.permissionsData = permissionsData;
    }

    @Override
    public void onEvent(ApplyTempPlayerPermissionEvent event) {
        Bukkit.getScheduler().runTask(InfoHeads.getInstance(), () -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
                if (player != null && player.isOnline()) {
                    PermissionAttachment attachment;

                    if (permissionsData.get(player.getUniqueId()) == null) {
                        PermissionAttachment permissionAttachment = player.addAttachment(InfoHeads.getInstance());
                        permissionsData.put(player.getUniqueId(), permissionAttachment);
                    }

                    attachment = permissionsData.get(player.getUniqueId());
                    attachment.setPermission(event.getPermission(), true);
                }
        });
    }
}
