package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;

public final class RemoveTempPlayerPermissionEvent extends ActionEvent {

    private final OnlinePlayer player;
    private final String permission;

    public RemoveTempPlayerPermissionEvent(OnlinePlayer player, String permission) {
        this.player = player;
        this.permission = permission;
    }

    public String getPermission() { return permission; }

    public Player getPlayer() {
        return player;
    }
}
