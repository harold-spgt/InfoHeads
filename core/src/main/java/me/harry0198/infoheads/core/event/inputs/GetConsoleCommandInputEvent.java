package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InputEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class GetConsoleCommandInputEvent extends InputEvent {

    public GetConsoleCommandInputEvent(InfoHeadProperties infoHeadProperties, OnlinePlayer onlinePlayer) {
        super(infoHeadProperties, onlinePlayer);
    }
}
