package me.harry0198.infoheads.core.model;

import java.util.Optional;
import java.util.UUID;

public abstract class OnlinePlayer extends Player {
    public OnlinePlayer(UUID uid, String username) {
        super(uid, username);
    }

    public abstract Optional<Location> getLookingAt();

    public abstract Location getLocation();

    public abstract boolean isSneaking();

    public abstract void sendMessage(String message);

    public abstract void performCommand(String command);

    public abstract boolean hasPermission(String permission);
    public abstract boolean isOnline();
    public abstract boolean isOp();
}
