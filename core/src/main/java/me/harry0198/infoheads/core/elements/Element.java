package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.hooks.PlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;

import java.io.Serializable;

public abstract class Element<T> implements Serializable {

    /**
     * Call to specific action
     */
    public abstract void performAction(EventDispatcher eventDispatcher, PlaceholderHandlingStrategy placeholderHandlingStrategy, OnlinePlayer player);

    /**
     * Get content of Element
     * @return Object of Element
     */
    public abstract T getContent();

    /**
     * Gets Type Element
     * @return InfoHeadType
     */
    public abstract InfoHeadType getType();

    public enum InfoHeadType {
        MESSAGE, CONSOLE_COMMAND, PLAYER_COMMAND, DELAY, PLAYER_PERMISSION
    }
}
