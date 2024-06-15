package me.harry0198.infoheads.core.event;

import me.harry0198.infoheads.core.model.InfoHeadProperties;

public class InfoHeadEvent extends Event {

    private final InfoHeadProperties infoHeadProperties;

    public InfoHeadEvent(InfoHeadProperties infoHeadProperties) {
        this.infoHeadProperties = infoHeadProperties;
    }

    public InfoHeadProperties getInfoHeadProperties() {
        return infoHeadProperties;
    }
}
