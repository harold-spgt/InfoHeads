package me.harry0198.infoheads.elements;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public final class PlayerCommandElement extends Element {

    private String command;

    public PlayerCommandElement(final String command) {
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
    public void performAction(Player player, PlayerInteractEvent event) {
        player.performCommand(removePlaceHolders(command, player, event));
    }

    @Override
    public Object getContent() {
        return command;
    }

    @Override
    public InfoHeadType getType() {
        return InfoHeadType.PLAYER_COMMAND;
    }
}
