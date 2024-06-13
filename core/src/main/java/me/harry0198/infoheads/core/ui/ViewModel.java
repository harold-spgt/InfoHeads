package me.harry0198.infoheads.core.ui;

import me.harry0198.infoheads.core.utils.SimpleProperty;

public abstract class ViewModel {

    private final SimpleProperty<Boolean> shouldCloseProperty;

    public ViewModel() {
        this.shouldCloseProperty = new SimpleProperty<>(false);
    }

    public SimpleProperty<Boolean> getShouldCloseProperty() {
        return shouldCloseProperty;
    }

    public void requestClose() {
        this.shouldCloseProperty.setValue(true);
    }
}
