package me.harry0198.infoheads.spigot.model;

import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Optional;

public class BukkitOnlinePlayer extends OnlinePlayer {

    private final Player player;

    public BukkitOnlinePlayer(Player player) {
        super(player.getUniqueId(), player.getName());
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Location> getLookingAt() {
        Block b = player.getTargetBlockExact(5);
        if (b == null) return Optional.empty();
        org.bukkit.Location bukkitLoc = b.getLocation();
        World world = bukkitLoc.getWorld();
        String dimension = world == null ? "world" : world.getName();

        return Optional.of(new Location(bukkitLoc.getBlockX(), bukkitLoc.getBlockY(), bukkitLoc.getBlockZ(), dimension));
    }

    @Override
    public Location getLocation() {
        org.bukkit.Location loc = player.getLocation();
        return new Location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public boolean isOp() {
        return player.isOp();
    }
}
