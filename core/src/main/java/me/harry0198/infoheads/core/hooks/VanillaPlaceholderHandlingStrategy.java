package me.harry0198.infoheads.core.hooks;

import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class VanillaPlaceholderHandlingStrategy implements PlaceholderHandlingStrategy {
    @Override
    public String replace(String message, OnlinePlayer player) {
        Location lookingAt = player.getLookingAt().orElse(new Location(0,0,0,"world"));
        Location stoodAt = player.getLocation();

        message = message.replace("{player-name}", player.getUsername());
        message = message.replace("{player-x}", String.valueOf(stoodAt.x()));
        message = message.replace("{player-y}", String.valueOf(stoodAt.y()));
        message = message.replace("{player-z}", String.valueOf(stoodAt.z()));
        message = message.replace("{block-x}", String.valueOf(lookingAt.x()));
        message = message.replace("{block-y}", String.valueOf(lookingAt.y()));
        message = message.replace("{block-z}", String.valueOf(lookingAt.z()));

        return message;
    }
}
