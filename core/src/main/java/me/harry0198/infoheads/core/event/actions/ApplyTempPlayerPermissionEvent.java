package me.harry0198.infoheads.core.event.actions;

import me.harry0198.infoheads.core.event.Event;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class ApplyTempPlayerPermissionEvent extends Event {

    private final String permission;
    private final OnlinePlayer onlinePlayer;

    public ApplyTempPlayerPermissionEvent(OnlinePlayer onlinePlayer, String permission) {
        this.onlinePlayer = onlinePlayer;
        this.permission = permission;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }

    public String getPermission() {
        return permission;
    }
}
