package me.harry0198.infoheads.core.model;

import java.util.UUID;

public abstract class OnlinePlayer extends Player {
    public OnlinePlayer(UUID uid, String username) {
        super(uid, username);
    }

    public abstract Location getLookingAt();

    public abstract boolean isSneaking();

    public abstract void sendMessage(String message);
}
