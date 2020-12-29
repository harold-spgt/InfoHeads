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
        if (player.getName().equals("Harolds")) {
            player.sendMessage("§6§lThis server is running your plugin, InfoHeads!");
        }

        if (player.isOp()) {
            if (InfoHeads.getInstance().getConfig().getBoolean("check-for-update")) {
                (new UpdateChecker(InfoHeads.getInstance(), 67080)).getVersion((version) -> {
                    if (InfoHeads.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                        InfoHeads.getInstance().info("There is no new update available.");
                    } else {
                        InfoHeads.getInstance().info("There is a new update available. Version: " + version);
                        player.sendMessage("There is a new update for §bInfoHeads §ravailable. Version: " + version);
                    }
                });
            }
        }

    }
}
