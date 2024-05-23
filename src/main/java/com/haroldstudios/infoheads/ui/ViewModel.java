package com.haroldstudios.infoheads.ui;


public class ViewModel {

    private final SimpleProperty<Boolean> shouldCloseProperty;

    public ViewModel() {
        this.shouldCloseProperty = new SimpleProperty<>(false);
    }

    public SimpleProperty<Boolean> getShouldCloseProperty() {
        return shouldCloseProperty;
    }


}
