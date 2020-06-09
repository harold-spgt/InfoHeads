package com.haroldstudios.infoheads.elements;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public final class MessageElement extends Element {

    private String message;

    /**
     * Class Constructor
     * @param message Sets message of element
     */
    public MessageElement(final String message) {
        this.message = message;
    }

    /**
     * Sets message of element
     * @param message Message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the message of element
     * @return Message of element
     */
    public String getMessage() {
        return message;
    }

    @Override
    public void performAction(@NotNull Player player, PlayerInteractEvent event) {
        player.sendMessage(removePlaceHolders(message, player, event));
    }

    @Override
    public Object getContent() {
        return message;
    }


    @Override
    public InfoHeadType getType() {
        return InfoHeadType.MESSAGE;
    }
}
