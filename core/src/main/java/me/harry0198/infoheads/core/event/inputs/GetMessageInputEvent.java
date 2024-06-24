package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InfoHeadEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class GetMessageInputEvent extends InfoHeadEvent {
    public GetMessageInputEvent(InfoHeadProperties infoHeadProperties) {
        super(infoHeadProperties);
    }
}
