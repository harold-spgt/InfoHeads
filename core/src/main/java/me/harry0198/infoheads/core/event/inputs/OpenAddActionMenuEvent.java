package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InfoHeadEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class OpenAddActionMenuEvent extends InfoHeadEvent {
    private final OnlinePlayer onlinePlayer;
    public OpenAddActionMenuEvent(InfoHeadProperties infoHeadProperties, OnlinePlayer onlinePlayer) {
        super(infoHeadProperties);
        this.onlinePlayer = onlinePlayer;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }
}
