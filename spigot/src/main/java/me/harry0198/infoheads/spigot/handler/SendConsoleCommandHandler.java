package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.dispatcher.EventListener;
import me.harry0198.infoheads.core.event.types.SendConsoleCommandEvent;
import me.harry0198.infoheads.spigot.EntryPoint;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Event handler for {@link SendConsoleCommandEvent}.
 * Console executes the provided {@link SendConsoleCommandEvent#getCommand()} when handled.
 */
public class SendConsoleCommandHandler implements EventListener<SendConsoleCommandEvent> {

    private static final Logger LOGGER = Logger.getLogger(SendConsoleCommandHandler.class.getName());
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(SendConsoleCommandEvent event) {
        LOGGER.log(Level.FINE, "Dispatching command {0}", event.getCommand());
        Bukkit.getScheduler().runTask(EntryPoint.getInstance(), () ->
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), event.getCommand()));
    }
}
