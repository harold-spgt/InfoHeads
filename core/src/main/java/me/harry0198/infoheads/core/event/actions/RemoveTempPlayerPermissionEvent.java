package me.harry0198.infoheads.core.event.actions;

import me.harry0198.infoheads.core.event.Event;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;

public class RemoveTempPlayerPermissionEvent extends Event {

    private final OnlinePlayer player;

    public RemoveTempPlayerPermissionEvent(OnlinePlayer player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
