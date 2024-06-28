package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InfoHeadEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class OpenAppendElementMenuEvent extends InfoHeadEvent {
    public OpenAppendElementMenuEvent(InfoHeadProperties infoHeadProperties) {
        super(infoHeadProperties);
    }
}
