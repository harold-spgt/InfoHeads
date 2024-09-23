package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.dispatcher.EventListener;
import me.harry0198.infoheads.core.event.types.ApplyTempPlayerPermissionEvent;
import me.harry0198.infoheads.spigot.util.Scheduler;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event handler for {@link ApplyTempPlayerPermissionEvent}.
 * Adds the provided permission {@link ApplyTempPlayerPermissionEvent#getPermission()} when handled.
 */
public class ApplyTempPermissionHandler implements EventListener<ApplyTempPlayerPermissionEvent> {

    private final Scheduler scheduler;
    private final Plugin plugin;
    private final ConcurrentHashMap<UUID, PermissionAttachment> permissionsData;

    public ApplyTempPermissionHandler(Plugin plugin, Scheduler scheduler, ConcurrentHashMap<UUID, PermissionAttachment> permissionsData) {
        this.permissionsData = permissionsData;
        this.scheduler = scheduler;
        this.plugin = plugin;
    }

    @Override
    public void onEvent(ApplyTempPlayerPermissionEvent event) {
        scheduler.scheduleEntity(event.getOnlinePlayer(), (player) -> {
            PermissionAttachment attachment;

            if (permissionsData.get(player.getUniqueId()) == null) {
                PermissionAttachment permissionAttachment = player.addAttachment(plugin);
                permissionsData.put(player.getUniqueId(), permissionAttachment);
            }

            attachment = permissionsData.get(player.getUniqueId());
            attachment.setPermission(event.getPermission(), true);
        });
    }
}
