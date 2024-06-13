package me.harry0198.infoheads.spigot.model;

import me.harry0198.infoheads.core.model.Location;

public class BukkitLocation extends Location {

    private final org.bukkit.Location location;

    public BukkitLocation(org.bukkit.Location location) {
        this.location = location;
    }
    @Override
    public int getX() {
        return location.getBlockX();
    }

    @Override
    public int getY() {
        return location.getBlockY();
    }

    @Override
    public int getZ() {
        return location.getBlockZ();
    }

    @Override
    public String getDimension() {
        if (location.getWorld() == null) {
            return "world";
        }

        return location.getWorld().getName();
    }
}
