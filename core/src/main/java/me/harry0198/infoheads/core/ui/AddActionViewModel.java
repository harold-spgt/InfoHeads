package me.harry0198.infoheads.core.ui;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.*;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class AddActionViewModel extends ViewModel {
    private final InfoHeadProperties configuration;

    @Inject
    public AddActionViewModel(EventDispatcher eventDispatcher, InfoHeadProperties configuration) {
        super(eventDispatcher);
        this.configuration = configuration;
    }

    public void newElement(Element.InfoHeadType infoHeadType, OnlinePlayer onlinePlayer) {
        getShouldCloseProperty().setValue(true);
        switch (infoHeadType) {
            case MESSAGE -> getEventDispatcher().dispatchEvent(new GetMessageInputEvent(configuration, onlinePlayer));
            case PLAYER_COMMAND -> getEventDispatcher().dispatchEvent(new GetPlayerCommandInputEvent(configuration, onlinePlayer));
            case DELAY -> getEventDispatcher().dispatchEvent(new GetDelayInputEvent(configuration, onlinePlayer));
            case CONSOLE_COMMAND -> getEventDispatcher().dispatchEvent(new GetConsoleCommandInputEvent(configuration, onlinePlayer));
            case PLAYER_PERMISSION -> getEventDispatcher().dispatchEvent(new GetPlayerPermissionInputEvent(configuration, onlinePlayer));
        }
    }
}
