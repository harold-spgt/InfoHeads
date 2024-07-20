package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.model.OnlinePlayer;

public final class SendPlayerMessageEvent extends ActionEvent {

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
