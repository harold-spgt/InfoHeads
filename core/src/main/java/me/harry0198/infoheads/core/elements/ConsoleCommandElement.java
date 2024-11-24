package me.harry0198.infoheads.core.elements;


import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendConsoleCommandEvent;
import me.harry0198.infoheads.core.hooks.PlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.model.OnlinePlayer;

import java.io.Serializable;

public final class ConsoleCommandElement extends Element<String> implements Serializable {

    private String command;

    public ConsoleCommandElement(final String command) {
        this.command = command;
    }

    /**
     * Sets the command
     * @param command Command to set without /
     */
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void performAction(EventDispatcher eventDispatcher, PlaceholderHandlingStrategy placeholderHandlingStrategy, OnlinePlayer player) {
        eventDispatcher.dispatchEvent(new SendConsoleCommandEvent(player, placeholderHandlingStrategy.replace(command, player)));
    }

    @Override
    public String getContent() {
        return command;
    }

    @Override
    public InfoHeadType getType() {
        return InfoHeadType.CONSOLE_COMMAND;
    }
}
