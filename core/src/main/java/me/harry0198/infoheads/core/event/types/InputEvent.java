package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public sealed class InputEvent extends Event
        permits GetConsoleCommandInputEvent,
        GetCoolDownInputEvent,
        GetDelayInputEvent,
        GetMessageInputEvent,
        GetNameInputEvent,
        GetPermissionInputEvent,
        GetPlayerCommandInputEvent,
        GetPlayerPermissionInputEvent
{

    private final OnlinePlayer onlinePlayer;
    private final InfoHeadProperties infoHeadProperties;
    public InputEvent(InfoHeadProperties infoHeadProperties, OnlinePlayer onlinePlayer) {
        this.infoHeadProperties = infoHeadProperties;
        this.onlinePlayer = onlinePlayer;
    }

    public InfoHeadProperties getInfoHeadProperties() {
        return infoHeadProperties;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }
}
