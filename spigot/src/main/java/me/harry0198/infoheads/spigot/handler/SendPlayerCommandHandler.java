package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.actions.SendPlayerCommandEvent;
import me.harry0198.infoheads.spigot.InfoHeads;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Event handler for {@link SendPlayerCommandEvent}.
 * Player executes the provided {@link SendPlayerCommandEvent#getCommand()} when handled.
 */
public class SendPlayerCommandHandler implements EventListener<SendPlayerCommandEvent> {

    private static final Logger LOGGER = Logger.getLogger(SendPlayerCommandHandler.class.getName());

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(SendPlayerCommandEvent event) {
        LOGGER.log(Level.FINE, "Handling Player Command...");
        Bukkit.getScheduler().runTask(InfoHeads.getInstance(), () -> {
            Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());

            if (player != null && player.isOnline()) {
                LOGGER.log(Level.FINE, "Performing Player Command /" + event.getCommand());
                player.chat("/" + event.getCommand());
            } else {
                LOGGER.log(Level.FINE, "Did not perform player command (Player=["+player+"], Online=[false])");
            }
        });
    }
}
