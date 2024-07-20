package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public final class OpenMenuMenuEvent extends MenuEvent {

    private final OnlinePlayer onlinePlayer;

    public OpenMenuMenuEvent(InfoHeadProperties infoHeadProperties, OnlinePlayer onlinePlayer) {
        super(infoHeadProperties);
        this.onlinePlayer = onlinePlayer;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }
}
