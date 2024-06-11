package com.haroldstudios.infoheads.ui;

public abstract class BaseGuiViewModel {

    private final SimpleProperty<Boolean> shouldCloseProperty;

    public BaseGuiViewModel() {
        this.shouldCloseProperty = new SimpleProperty<>(false);
    }

    public SimpleProperty<Boolean> getShouldCloseProperty() {
        return shouldCloseProperty;
    }

    public void requestClose() {
        this.shouldCloseProperty.setValue(true);
    }
}
