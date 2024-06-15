package me.harry0198.infoheads.spigot.listener;

import me.harry0198.infoheads.core.event.handlers.*;
import me.harry0198.infoheads.core.model.HandAction;
import me.harry0198.infoheads.spigot.util.MappingUtil;
import me.harry0198.infoheads.spigot.model.BukkitOnlinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitEventListener implements Listener {

    private final BreakHandler breakHandler;
    private final InteractHandler interactHandler;
    private final PlaceHandler placeHandler;
    private final PlayerJoinHandler joinHandler;
    private final PlayerQuitHandler quitHandler;

    public BukkitEventListener(
            BreakHandler breakHandler,
            InteractHandler interactHandler,
            PlaceHandler placeHandler,
            PlayerJoinHandler joinHandler,
            PlayerQuitHandler quitHandler
    ) {
        this.breakHandler = breakHandler;
        this.interactHandler = interactHandler;
        this.placeHandler = placeHandler;
        this.joinHandler = joinHandler;
        this.quitHandler = quitHandler;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        breakHandler.handle(new BukkitOnlinePlayer(event.getPlayer()), MappingUtil.from(event.getBlock().getLocation()));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            HandAction handAction = switch (event.getAction()) {
                case LEFT_CLICK_BLOCK -> HandAction.LEFT_CLICK;
                case RIGHT_CLICK_BLOCK -> HandAction.RIGHT_CLICK;
                default -> null;
            };

            if (handAction == null) return;

            interactHandler.interactWithHead(
                    new BukkitOnlinePlayer(event.getPlayer()),
                    MappingUtil.from((event.getClickedBlock().getLocation())),
                    handAction
                    );
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        placeHandler.placeHead(new BukkitOnlinePlayer(event.getPlayer()), MappingUtil.from((event.getBlock().getLocation())));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        joinHandler.onJoinEvent(new BukkitOnlinePlayer(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        quitHandler.onQuit(new BukkitOnlinePlayer(event.getPlayer()));
    }
}
