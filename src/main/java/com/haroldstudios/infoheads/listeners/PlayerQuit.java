package com.haroldstudios.infoheads.listeners;

import com.haroldstudios.infoheads.datastore.DataStore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (event.getPlayer() != null && DataStore.getPermissionsData().get(event.getPlayer().getUniqueId()) != null) {
            Map<String, Boolean> perms = new HashMap<>(DataStore.getPermissionsData().get(event.getPlayer().getUniqueId()).getPermissions());

            perms.forEach((perm, bool) -> {
                if (!bool) return;

                DataStore.getPermissionsData().get(event.getPlayer().getUniqueId()).unsetPermission(perm);
            });
        }
    }
}
