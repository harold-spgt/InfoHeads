package me.harry0198.infoheads.core.service;

import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserStateService {

    private final Map<UUID, InfoHeadProperties> placerMode = new HashMap<>();

    public void addToPlacerMode(Player player, InfoHeadProperties infoHeadProperties) {
        this.placerMode.put(player.getUid(), infoHeadProperties);
    }

    public void removeFromPlacerMode(Player player) {
        this.placerMode.remove(player.getUid());
    }

    public Optional<InfoHeadProperties> getPlacerModeHead(Player player) {
        return Optional.ofNullable(this.placerMode.get(player.getUid()));
    }
}
