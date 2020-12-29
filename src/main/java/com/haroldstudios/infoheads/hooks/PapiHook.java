package com.haroldstudios.infoheads.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public final class PapiHook {

    public static String execute(Player player, String string) {
        return PlaceholderAPI.setPlaceholders(player, string);
    }

}
