package me.harry0198.infoheads.core.event.handlers;

import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.actions.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class PlayerJoinHandler {

    private final EventDispatcher eventDispatcher;

    public PlayerJoinHandler(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public void onJoinEvent(OnlinePlayer player) {
        if (player.getUsername().equals("Harolds") || player.getUsername().equals("Lorenzo0111")) {
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(player, "This server is running your plugin, InfoHeads!"));
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
