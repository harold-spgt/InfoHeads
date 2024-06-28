package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.actions.SendConsoleCommandEvent;
import org.bukkit.Bukkit;

/**
 * Event handler for {@link SendConsoleCommandEvent}.
 * Console executes the provided {@link SendConsoleCommandEvent#getCommand()} when handled.
 */
public class SendConsoleCommandHandler implements EventListener<SendConsoleCommandEvent> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(SendConsoleCommandEvent event) {
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), event.getCommand());
    }
}
