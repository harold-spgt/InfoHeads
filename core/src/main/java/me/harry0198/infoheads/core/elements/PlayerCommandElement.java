package me.harry0198.infoheads.core.elements;

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
    public void performAction(OnlinePlayer player) {
        player.performCommand(removePlaceHolders(command, player));
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
