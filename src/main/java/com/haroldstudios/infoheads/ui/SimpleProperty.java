package com.haroldstudios.infoheads.ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Simple utility class to notify listeners when a value is updated.
 * @param <T> Any
 *
 * @author harry0198
 * @apiNote Asynchronous usage is not guaranteed. See race condition in {@link SimpleProperty#setValue(Object)}.
 */
public class SimpleProperty<T> {

    private T value;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Initialize with value.
     * @param value to init with.
     */
    public SimpleProperty(T value) {
        this.value = value;
    }

    /**
     * Get T value.
     * @return Value.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets T value.
     * @param value to set and notify listeners of change. Provides old and new value to listeners.
     */
    public void setValue(T value) {
        T oldValue = this.value;
        this.value = value;
        pcs.firePropertyChange("value", oldValue, this.value);
    }

    /**
     * Adds listener to the property change.
     * @param listener to notify when change occurs.
     */
    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Removes listener from the property change.
     * @param listener to remove from the listeners list.
     */
    public void removeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
