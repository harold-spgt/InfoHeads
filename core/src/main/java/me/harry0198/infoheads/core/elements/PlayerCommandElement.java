package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerCommandEvent;
import me.harry0198.infoheads.core.hooks.PlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.model.OnlinePlayer;

import java.io.Serializable;

public final class PlayerCommandElement extends Element<String> implements Serializable {

    private String command;

    public PlayerCommandElement(String command) {
        this.command = command;
    }

    /**
     * Gets Command
     * @return Command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Sets command
     * @param command to Set
     */
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void performAction(EventDispatcher eventDispatcher, PlaceholderHandlingStrategy placeholderHandlingStrategy, OnlinePlayer player) {
        eventDispatcher.dispatchEvent(new SendPlayerCommandEvent(player, placeholderHandlingStrategy.replace(command, player)));
    }

    @Override
    public String getContent() {
        return command;
    }

    @Override
    public InfoHeadType getType() {
        return InfoHeadType.PLAYER_COMMAND;
    }
}
