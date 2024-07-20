package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.model.OnlinePlayer;

public final class GivePlayerHeadsEvent extends ActionEvent {

    private final OnlinePlayer onlinePlayer;

    public GivePlayerHeadsEvent(OnlinePlayer onlinePlayer) {
        this.onlinePlayer = onlinePlayer;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }
}
