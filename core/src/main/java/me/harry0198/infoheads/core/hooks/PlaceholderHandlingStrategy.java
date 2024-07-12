package me.harry0198.infoheads.core.hooks;

import me.harry0198.infoheads.core.model.OnlinePlayer;

@FunctionalInterface
public interface PlaceholderHandlingStrategy {
    String replace(String str, OnlinePlayer onlinePlayer);
}
