package me.harry0198.infoheads.core.model;

import java.util.UUID;

/**
 * Snapshot of the player's details.
 */
public final class PlayerDetailSnapshot {

    private final UUID playerId;
    private final Location lookingAt;

    /**
     * A snapshot in time of the player's details.
     * @param playerId Unique ID of the player.
     * @param lookingAt {@link Location} the player is looking at or null if not looking at anything (e.g. air).
     */
    public PlayerDetailSnapshot(UUID playerId, Location lookingAt) {
        this.playerId = playerId;
        this.lookingAt = lookingAt;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    /**
     * Gets the location that the player is currently looking at.
     * @return {@link Location} currently looking at or null if not.
     */
    public Location getLookingAt() {
        return lookingAt;
    }
}
