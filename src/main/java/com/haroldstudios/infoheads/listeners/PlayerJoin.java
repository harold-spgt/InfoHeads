package com.haroldstudios.infoheads.listeners;


import com.haroldstudios.infoheads.InfoHeads;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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

                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

                    String latest;

                    try {

                        URL url = new URL("https://raw.githubusercontent.com/harry0198/InfoHeads-mvn/master/pom.xml");

                        // read text returned by server
                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                        String line;
                        while ((line = in.readLine()) != null) {
                            // All my versions should always start with "v-"
                            if (line.contains("<version>") && line.contains("</version>")) {
                                line = line.split(">")[1];
                                line = line.split("<")[0];
                                if (!line.startsWith("v-")) continue;

                                latest = line.split("v-")[1];

                                String finalLatest = latest;
                                Bukkit.getScheduler().runTask(plugin, () -> {
                                    if (finalLatest != null)
                                        if (!plugin.getDescription().getVersion().equalsIgnoreCase(finalLatest)) {
                                            plugin.info("There is a new update available.");
                                            player.sendMessage("There is a new update for §bInfoHeads §ravailable.");
                                        }
                                });
                                in.close();
                                return;
                            }
                        }
                        in.close();

                    } catch (IOException ex) {
                        plugin.error("Unable to fetch updates!");
                        ex.printStackTrace();
                    }
                });
            }
        }
    }
}
