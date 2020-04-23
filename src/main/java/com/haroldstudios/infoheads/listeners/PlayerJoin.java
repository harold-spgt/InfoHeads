package com.haroldstudios.infoheads.listeners;


import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.utils.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoin implements Listener {

    private final InfoHeads plugin;

    public PlayerJoin(InfoHeads plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.getName().equals("Harolds")) {
            player.sendMessage("§6§lThis server is running your plugin, InfoHeads!");
        }

        if (player.isOp()) {
            if (plugin.getConfig().getBoolean("check-for-update")) {
                (new UpdateChecker(plugin, 67080)).getVersion((version) -> {
                    if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                        plugin.info("There is not a new update available.");
                    } else {
                        plugin.info("There is a new update available.");
                        player.sendMessage("There is a new update for §bInfoHeads §ravailable.");
                    }
                });
            }
        }
    }
}
