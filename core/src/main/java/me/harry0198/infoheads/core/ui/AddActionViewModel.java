package me.harry0198.infoheads.core.ui;

import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.inputs.*;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.utils.SimpleProperty;

public class AddActionViewModel extends ViewModel {
    private final InfoHeadProperties configuration;

    public AddActionViewModel(EventDispatcher eventDispatcher, InfoHeadProperties configuration) {
        super(eventDispatcher);
        this.configuration = configuration;
    }

    public InfoHeadProperties getConfiguration() {
        return configuration;
    }

    public void newElement(Element.InfoHeadType infoHeadType, OnlinePlayer onlinePlayer) {
        getShouldCloseProperty().setValue(true);
        switch (infoHeadType) {
            case MESSAGE -> getEventDispatcher().dispatchEvent(new GetMessageInputEvent(configuration, onlinePlayer));
            case PLAYER_COMMAND -> getEventDispatcher().dispatchEvent(new GetPlayerCommandInputEvent(configuration, onlinePlayer));
            case DELAY -> getEventDispatcher().dispatchEvent(new GetDelayInputEvent(configuration, onlinePlayer));
            case CONSOLE_COMMAND -> getEventDispatcher().dispatchEvent(new GetConsoleCommandInputEvent(configuration, onlinePlayer));
            case PERMISSION -> getEventDispatcher().dispatchEvent(new GetPermissionInputEvent(configuration, onlinePlayer));
            case PLAYER_PERMISSION -> getEventDispatcher().dispatchEvent(new GetPlayerPermissionInputEvent(configuration, onlinePlayer));
        }
    }
}
