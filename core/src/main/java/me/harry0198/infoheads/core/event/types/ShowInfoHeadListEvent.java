package me.harry0198.infoheads.core.event.types;

import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;

import java.util.Collection;

public final class ShowInfoHeadListEvent extends ActionEvent {
    private final Collection<InfoHeadProperties> infoHeadPropertiesList;
    private final OnlinePlayer onlinePlayer;
    public ShowInfoHeadListEvent(OnlinePlayer onlinePlayer, Collection<InfoHeadProperties> infoHeadPropertiesList) {
        this.infoHeadPropertiesList = infoHeadPropertiesList;
        this.onlinePlayer = onlinePlayer;
    }

    public OnlinePlayer getOnlinePlayer() {
        return onlinePlayer;
    }

    public Collection<InfoHeadProperties> getInfoHeadPropertiesList() {
        return infoHeadPropertiesList;
    }
}
