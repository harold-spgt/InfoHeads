package me.harry0198.infoheads.core.event.inputs;

import me.harry0198.infoheads.core.event.InfoHeadEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

public class GetPlayerPermissionInputEvent extends InfoHeadEvent {
    public GetPlayerPermissionInputEvent(InfoHeadProperties infoHeadProperties) {
        super(infoHeadProperties);
    }
}
