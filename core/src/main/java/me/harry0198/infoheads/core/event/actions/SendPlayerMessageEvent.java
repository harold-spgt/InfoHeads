package me.harry0198.infoheads.core.event.actions;

import me.harry0198.infoheads.core.event.Event;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class SendPlayerMessageEvent extends Event {

    private final String message;
    private final OnlinePlayer onlinePlayer;

    public SendPlayerMessageEvent(OnlinePlayer onlinePlayer, String message) {
        this.message = message;
        this.onlinePlayer = onlinePlayer;
    }

    public String getMessage() {
        return message;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }
}
