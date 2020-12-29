package com.haroldstudios.infoheads.elements;

import com.haroldstudios.infoheads.InfoHeads;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public final class ConsoleCommandElement extends Element {

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
    public void performAction(@NotNull Player player, PlayerInteractEvent event) {
        InfoHeads.getInstance().getServer().dispatchCommand(InfoHeads.getInstance().getServer().getConsoleSender(), removePlaceHolders(command, player, event));
    }

    @Override
    public Object getContent() {
        return command;
    }

    @Override
    public InfoHeadType getType() {
        return InfoHeadType.CONSOLE_COMMAND;
    }
}
