package me.harry0198.infoheads.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PapiMethod {

    public String execute(Player player, String string) {
        return PlaceholderAPI.setPlaceholders(player, string);
    }
}
