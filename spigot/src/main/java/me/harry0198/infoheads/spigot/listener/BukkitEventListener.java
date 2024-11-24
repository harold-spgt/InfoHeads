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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BukkitEventListener implements Listener {

    private final BreakHandler breakHandler;
    private final InteractHandler interactHandler;
    private final PlaceHandler placeHandler;
    private final PlayerJoinHandler joinHandler;
    private final PlayerQuitHandler quitHandler;
    private final Map<UUID, PermissionAttachment> permissionAttachmentMap;

    public BukkitEventListener(
            BreakHandler breakHandler,
            InteractHandler interactHandler,
            PlaceHandler placeHandler,
            PlayerJoinHandler joinHandler,
            PlayerQuitHandler quitHandler,
            Map<UUID, PermissionAttachment> permissionAttachmentConcurrentHashMap
    ) {
        this.breakHandler = breakHandler;
        this.interactHandler = interactHandler;
        this.placeHandler = placeHandler;
        this.joinHandler = joinHandler;
        this.quitHandler = quitHandler;
        this.permissionAttachmentMap = permissionAttachmentConcurrentHashMap;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        breakHandler.handle(new BukkitOnlinePlayer(event.getPlayer()), MappingUtil.from(event.getBlock().getLocation()));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getHand() == EquipmentSlot.HAND) {
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
        PermissionAttachment permissionAttachment = permissionAttachmentMap.get(event.getPlayer().getUniqueId());
        List<String> permissions = new ArrayList<>();
        if (permissionAttachment != null) {
            permissionAttachment.getPermissions().forEach((perm, bool) -> {
                if (Boolean.FALSE.equals(bool)) return;

                permissions.add(perm);
            });
        }

        quitHandler.onQuit(new BukkitOnlinePlayer(event.getPlayer()), permissions);
    }
}
