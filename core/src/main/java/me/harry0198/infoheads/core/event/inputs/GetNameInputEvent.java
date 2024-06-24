package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InfoHeadEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class GetNameInputEvent extends InfoHeadEvent {
    public GetNameInputEvent(InfoHeadProperties infoHeadProperties) {
        super(infoHeadProperties);
    }
}
