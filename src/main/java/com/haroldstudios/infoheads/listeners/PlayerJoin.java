package com.haroldstudios.infoheads.listeners;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.utils.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoin implements Listener {

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.getName().equals("Harolds") || player.getName().equals("Lorenzo0111")) {
            player.sendMessage("§6§lThis server is running your plugin, InfoHeads!");
        }

        if (player.isOp()) {
            if (InfoHeads.getInstance().getConfig().getBoolean("check-for-update")) {
                (new UpdateChecker(InfoHeads.getInstance(), 67080)).getVersion((version) -> {
                    if (!InfoHeads.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                        player.sendMessage("There is a new update for §bInfoHeads §ravailable. Version: " + version);
                    }
                });
            }
        }

    }
}
