package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.event.InfoHeadEvent;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class OpenInfoHeadMenuEvent extends InfoHeadEvent {

    private final OnlinePlayer onlinePlayer;

    public OpenInfoHeadMenuEvent(InfoHeadProperties infoHeadProperties, OnlinePlayer onlinePlayer) {
        super(infoHeadProperties);
        this.onlinePlayer = onlinePlayer;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }
}
