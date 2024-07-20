package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.model.OnlinePlayer;

public final class ApplyTempPlayerPermissionEvent extends ActionEvent {

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
