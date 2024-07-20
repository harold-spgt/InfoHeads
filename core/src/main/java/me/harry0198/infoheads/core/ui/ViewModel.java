package me.harry0198.infoheads.core.ui;

import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.utils.SimpleProperty;

public abstract class ViewModel {

    private final SimpleProperty<Boolean> shouldCloseProperty;
    private final EventDispatcher eventDispatcher;

    public ViewModel(EventDispatcher eventDispatcher) {
        this.shouldCloseProperty = new SimpleProperty<>(false);
        this.eventDispatcher = eventDispatcher;
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public SimpleProperty<Boolean> getShouldCloseProperty() {
        return shouldCloseProperty;
    }

    public void requestClose() {
        this.shouldCloseProperty.setValue(true);
    }
}
