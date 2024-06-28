package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InputEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class GetPlayerCommandInputEvent extends InputEvent {
    public GetPlayerCommandInputEvent(InfoHeadProperties infoHeadProperties, OnlinePlayer onlinePlayer) {
        super(infoHeadProperties, onlinePlayer);
    }
}
