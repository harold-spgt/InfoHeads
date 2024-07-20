package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public final class OpenAddActionMenuEvent extends MenuEvent {
    private final OnlinePlayer onlinePlayer;
    public OpenAddActionMenuEvent(InfoHeadProperties infoHeadProperties, OnlinePlayer onlinePlayer) {
        super(infoHeadProperties);
        this.onlinePlayer = onlinePlayer;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }
}
