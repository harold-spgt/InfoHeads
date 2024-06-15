package me.harry0198.infoheads.spigot.util;

import me.harry0198.infoheads.core.model.Location;

public class MappingUtil {

    public static Location from(org.bukkit.Location location) {
        return new Location(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getWorld() == null ? "world" : location.getWorld().getName()
        );
    }
}
