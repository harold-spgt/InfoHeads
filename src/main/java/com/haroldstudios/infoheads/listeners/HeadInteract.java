package com.haroldstudios.infoheads.listeners;

import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.elements.EndElement;
import com.haroldstudios.infoheads.utils.Constants;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class HeadInteract implements Listener {

    private final InfoHeads plugin;

    public HeadInteract(InfoHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void interactWithHead(PlayerInteractEvent e) {
        // Is a valid block & has basic use perms
        if (isOffHand(e) || e.getClickedBlock() == null || !e.getPlayer().hasPermission(Constants.BASE_PERM + "use"))
            return;

        Map<Location, InfoHeadConfiguration> infoHeads = plugin.getDataStore().getInfoHeads();

        // Checks if there is an infohead at this location
        if (!infoHeads.containsKey(e.getClickedBlock().getLocation())) return;

        if (e.getPlayer().isSneaking() && e.getAction().equals(Action.LEFT_CLICK_BLOCK))
            return;

        e.setCancelled(true);

        String permission = plugin.getDataStore().getInfoHeads().get(e.getClickedBlock().getLocation()).getPermission();
        InfoHeadConfiguration configuration = infoHeads.get(e.getClickedBlock().getLocation());

        // Checks if player has infohead specific perms
        if (permission != null)
            if (!e.getPlayer().hasPermission(permission)) {
                MessageUtil.sendMessage(e.getPlayer(), MessageUtil.NO_PERMISSION);
                return;
            }

        // Checks if player is on cooldown

        if (configuration.getTimestamps().containsKey(e.getPlayer().getUniqueId())) {

            long playerTimestamp = configuration.getTimestamps().get(e.getPlayer().getUniqueId());
            if (playerTimestamp > System.currentTimeMillis()) {
                e.getPlayer().sendMessage(MessageUtil.returnTimeMessage(playerTimestamp - System.currentTimeMillis(), plugin.getMessagesConfig().getString("cooldown")));
                return;
            } else {
                configuration.getTimestamps().remove(e.getPlayer().getUniqueId());
            }
        }

        if (configuration.getExecuted().contains(e.getPlayer().getUniqueId())) {
            MessageUtil.sendMessage(e.getPlayer(), plugin.getMessagesConfig().getString("one-time"));
            return;
        }

        final List<Element> elements = new ArrayList<>(plugin.getDataStore().getInfoHeads().get(e.getClickedBlock().getLocation()).getElementList());
        elements.add(new EndElement(elements));

        // Loops through elements
        Iterator<Element> element = elements.iterator();
        int time = 0;
        while (element.hasNext()) {
            Element el = element.next();
            if (el.getType().equals(Element.InfoHeadType.DELAY))
                time = time + (int) el.getContent();

            Bukkit.getScheduler().runTaskLater(plugin, () -> {

                if (e.getPlayer() != null && e.getPlayer().isOnline())
                    el.performAction(e.getPlayer(), e);

            }, time * 20L);
        }

        // Permission Removal -> EndElement

        // If Cooldown Exists, add it to player's stamp
        if (configuration.getCooldown() != null) {
            configuration.getTimestamps().put(e.getPlayer().getUniqueId(), System.currentTimeMillis() + configuration.getCooldown());
        }

        if (configuration.isOnce()) {
            configuration.getExecuted().add(e.getPlayer().getUniqueId());
        }

    }

    /**
     * Define if the hand used in event is off hand
     *
     * @param event Event to analyse
     * @return Is off hand
     */
    private boolean isOffHand(final PlayerInteractEvent event) {
        try {
            return event.getHand() == EquipmentSlot.OFF_HAND;
        } catch (NoSuchMethodError e) {
            return false;
        }
    }

}
