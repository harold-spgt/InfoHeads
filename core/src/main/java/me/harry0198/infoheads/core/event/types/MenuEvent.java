package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public sealed class MenuEvent extends Event permits OpenAddActionMenuEvent, OpenCoolDownMenuEvent, OpenMenuMenuEvent {

    private final InfoHeadProperties infoHeadProperties;

    public MenuEvent(InfoHeadProperties infoHeadProperties) {
        this.infoHeadProperties = infoHeadProperties;
    }

    public InfoHeadProperties getInfoHeadProperties() {
        return infoHeadProperties;
    }
}
