package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.actions.SendPlayerCommandEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public final class PlayerCommandElement extends Element<String> {

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
    public void performAction(EventDispatcher eventDispatcher, OnlinePlayer player) {
        eventDispatcher.dispatchEvent(new SendPlayerCommandEvent(player, command));
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
