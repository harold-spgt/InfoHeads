package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public abstract class Element<T> {

    /**
     * Call to specific action
     */
    public abstract void performAction(EventDispatcher eventDispatcher, OnlinePlayer player);

    /**
     * Get content of Element
     * @return Object of Element
     */
    public abstract T getContent();

    /**
     * Gets Type Element
     * @return InfoHeadType
     */
    public abstract InfoHeadType getType();

    protected String removePlaceHolders(String message, OnlinePlayer player) {
//        message = ChatColor.translateAlternateColorCodes('&', message);
        Location lookingAt = player.getLookingAt().orElse(new Location(0,0,0,"world"));
        Location stoodAt = player.getLocation();

        message = message.replace("{player-name}", player.getUsername());
        message = message.replace("{player-x}", String.valueOf(stoodAt.x()));
        message = message.replace("{player-y}", String.valueOf(stoodAt.y()));
        message = message.replace("{player-z}", String.valueOf(stoodAt.z()));
        message = message.replace("{block-x}", String.valueOf(lookingAt.x()));
        message = message.replace("{block-y}", String.valueOf(lookingAt.y()));
        message = message.replace("{block-z}", String.valueOf(lookingAt.z()));
//        if (InfoHeads.getInstance().papi)
//            message = PapiHook.execute(player, message);
        return message;
    }

    public enum InfoHeadType {
        MESSAGE, CONSOLE_COMMAND, PLAYER_COMMAND, DELAY, PLAYER_PERMISSION, END, PERMISSION, RENAME
    }
}
