package me.harry0198.infoheads.core.event.handlers;

import me.harry0198.infoheads.core.model.OnlinePlayer;

public class PlayerJoinHandler {

    public void onJoinEvent(OnlinePlayer player) {
        if (player.getUsername().equals("Harolds") || player.getUsername().equals("Lorenzo0111")) {
            player.sendMessage("§6§lThis server is running your plugin, InfoHeads!");
        }

        if (player.isOp()) {
            //TODO
//            if (InfoHeads.getInstance().getConfig().getBoolean("check-for-update")) {
//                (new UpdateChecker(67080)).getVersion((version) -> {
//                    if (!InfoHeads.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
//                        player.sendMessage("There is a new update for §bInfoHeads §ravailable. Version: " + version);
//                    }
//                });
//            }
        }

    }
}
