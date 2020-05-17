package com.haroldstudios.infoheads.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoin implements Listener {

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.getName().equals("Harolds")) {
            player.sendMessage("§6§lThis server is running your plugin, InfoHeads!");
        }

    }
}
