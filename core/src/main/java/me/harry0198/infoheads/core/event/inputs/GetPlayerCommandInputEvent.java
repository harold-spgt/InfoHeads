package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InfoHeadEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class GetPlayerCommandInputEvent extends InfoHeadEvent {
    public GetPlayerCommandInputEvent(InfoHeadProperties infoHeadProperties) {
        super(infoHeadProperties);
    }
}
