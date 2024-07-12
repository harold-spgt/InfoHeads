package me.harry0198.infoheads.spigot.util;

import me.harry0198.infoheads.core.model.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class MappingUtil {

    public static Location from(org.bukkit.Location location) {
        return new Location(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getWorld() == null ? "world" : location.getWorld().getName()
        );
    }

    public static org.bukkit.Location to(Location location) {
        World world = Bukkit.getWorld(location.dimension());
        if (world == null) {
            world = Bukkit.getWorlds().get(0);
        }
        return new org.bukkit.Location(world, location.x(), location.y(), location.z());
    }
}
