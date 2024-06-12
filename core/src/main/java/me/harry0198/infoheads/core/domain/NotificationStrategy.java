package me.harry0198.infoheads.core.domain;

import me.harry0198.infoheads.core.model.PlayerDetailSnapshot;

public interface NotificationStrategy {

    void send(PlayerDetailSnapshot snapshot, String notification);
}
