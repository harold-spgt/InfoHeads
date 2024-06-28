package me.harry0198.infoheads.core.event.actions;

import me.harry0198.infoheads.core.event.Event;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;

public class RemoveTempPlayerPermissionEvent extends Event {

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
