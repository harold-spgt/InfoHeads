package me.harry0198.infoheads.core.elements;


import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.actions.SendConsoleCommandEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;

import java.io.Serializable;

public final class ConsoleCommandElement extends Element<String> implements Serializable {

    private String command;

    public ConsoleCommandElement(final String command) {
        this.command = command;
    }

    /**
     * Gets command in String form
     * @return Command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets the command
     * @param command Command to set without /
     */
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void performAction(EventDispatcher eventDispatcher, OnlinePlayer player) {
        eventDispatcher.dispatchEvent(new SendConsoleCommandEvent(player, removePlaceHolders(command, player)));
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
