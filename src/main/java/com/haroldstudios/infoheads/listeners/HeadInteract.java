package com.haroldstudios.infoheads.listeners;

import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.utils.Constants;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import java.util.Iterator;
import java.util.Map;

public final class HeadInteract implements Listener {

    private InfoHeads plugin;

    public HeadInteract(InfoHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void interactWithHead(PlayerInteractEvent e) {
        if (isOffHand(e) || e.getClickedBlock() == null || !e.getPlayer().hasPermission(Constants.BASE_PERM + "use"))
            return;

        Map<Location, InfoHeadConfiguration> infoHeads = plugin.getDataStore().getInfoHeads();

        if (!infoHeads.containsKey(e.getClickedBlock().getLocation())) return;

        e.setCancelled(true);

        String permission = plugin.getDataStore().getInfoHeads().get(e.getClickedBlock().getLocation()).getPermission();

        if (permission != null)
            if (!e.getPlayer().hasPermission(permission)) {
                MessageUtil.sendMessage(e.getPlayer(), MessageUtil.NO_PERMISSION);
                return;
            }


        Iterator<Element> element = plugin.getDataStore().getInfoHeads().get(e.getClickedBlock().getLocation()).getElementList().iterator();
        int time = 0;
        while (element.hasNext()) {
            Element el = element.next();
            if (el.getType().equals(Element.InfoHeadType.DELAY))
                time = time + (int) el.getContent();
            Bukkit.getScheduler().runTaskLater(plugin, () -> el.performAction(e.getPlayer(), e), time * 20);
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
