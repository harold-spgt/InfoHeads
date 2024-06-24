package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InfoHeadEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class GetConsoleCommandInputEvent extends InfoHeadEvent {
    public GetConsoleCommandInputEvent(InfoHeadProperties infoHeadProperties) {
        super(infoHeadProperties);
    }
}
