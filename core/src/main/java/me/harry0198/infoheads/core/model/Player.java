package me.harry0198.infoheads.core.model;

import java.util.UUID;

public class Player {

    private final UUID uid;
    private final String username;

    public Player(UUID uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUid() {
        return uid;
    }
}
