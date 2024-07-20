package me.harry0198.infoheads.core.event.handlers;

import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.utils.Constants;

public class PlayerJoinHandler {

    private final EventDispatcher eventDispatcher;

    public PlayerJoinHandler(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public void onJoinEvent(OnlinePlayer player) {
        if (player.getUsername().equals("Harolds") || player.getUsername().equals("Lorenzo0111")) {
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(player, "This server is running your plugin, InfoHeads!"));
        }
    }
}
