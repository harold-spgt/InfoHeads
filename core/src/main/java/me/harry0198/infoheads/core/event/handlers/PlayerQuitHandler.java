package me.harry0198.infoheads.core.event.handlers;

import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.actions.RemoveTempPlayerPermissionEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class PlayerQuitHandler {

    private final EventDispatcher eventDispatcher;

    public PlayerQuitHandler(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public void onQuit(OnlinePlayer player) {
        eventDispatcher.dispatchEvent(new RemoveTempPlayerPermissionEvent(player));
    }
}
