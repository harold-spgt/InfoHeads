package me.harry0198.infoheads.spigot.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.harry0198.infoheads.core.hooks.PlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.hooks.VanillaPlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PAPIPlaceholderHandlingStrategy implements PlaceholderHandlingStrategy {

    private final PlaceholderHandlingStrategy vanillaStrategy = new VanillaPlaceholderHandlingStrategy();

    @Override
    public String replace(String str, OnlinePlayer onlinePlayer) {
        String message = vanillaStrategy.replace(str, onlinePlayer);

        Player player = Bukkit.getPlayer(onlinePlayer.getUid());
        if (player == null || !player.isOnline()) return message;

        return PlaceholderAPI.setPlaceholders(player, message);
    }
}
