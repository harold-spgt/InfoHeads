package me.harry0198.infoheads.core.event.handlers;

import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.RemoveTempPlayerPermissionEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;

import java.util.List;

public class PlayerQuitHandler {

    private final EventDispatcher eventDispatcher;

    public PlayerQuitHandler(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public void onQuit(OnlinePlayer player, List<String> permissionsToRemove) {
                permissionsToRemove.forEach(permission ->
                        eventDispatcher.dispatchEvent(new RemoveTempPlayerPermissionEvent(player, permission)));
    }
}
