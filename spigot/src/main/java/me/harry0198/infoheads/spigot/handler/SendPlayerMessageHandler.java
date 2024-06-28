package me.harry0198.infoheads.spigot.handler;

import me.harry0198.infoheads.core.event.EventListener;
import me.harry0198.infoheads.core.event.actions.SendPlayerMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


/**
 * Event handler for {@link SendPlayerMessageEvent}.
 * Sends the player the provided {@link SendPlayerMessageEvent#getMessage()} when handled.
 */
public class SendPlayerMessageHandler implements EventListener<SendPlayerMessageEvent> {

    /**
     * If the player is online, sends the player a message.
     * @param event {@link SendPlayerMessageEvent} to handle.
     */
    @Override
    public void onEvent(SendPlayerMessageEvent event) {
        Player player = Bukkit.getPlayer(event.getOnlinePlayer().getUid());
        if (player != null) player.sendMessage(event.getMessage());
    }
}
