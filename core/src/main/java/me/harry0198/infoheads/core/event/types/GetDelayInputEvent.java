package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public final class GetDelayInputEvent extends InputEvent {
    public GetDelayInputEvent(InfoHeadProperties infoHeadProperties, OnlinePlayer onlinePlayer) {
        super(infoHeadProperties, onlinePlayer);
    }
}
