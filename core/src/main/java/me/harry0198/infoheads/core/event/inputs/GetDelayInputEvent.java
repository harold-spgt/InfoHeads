package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InputEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class GetDelayInputEvent extends InputEvent {
    public GetDelayInputEvent(InfoHeadProperties infoHeadProperties, OnlinePlayer onlinePlayer) {
        super(infoHeadProperties, onlinePlayer);
    }
}
