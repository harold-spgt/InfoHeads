package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.actions.SendPlayerCommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Event handler for {@link SendPlayerCommandEvent}.
 * Player executes the provided {@link SendPlayerCommandEvent#getCommand()} when handled.
 */
public class SendPlayerCommandHandler implements EventListener<SendPlayerCommandEvent> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(SendPlayerCommandEvent event) {
        Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
        if (player != null && player.isOnline())
            player.chat("/" + event.getCommand());
    }
}
