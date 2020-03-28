package me.harry0198.infoheads.listeners;

import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.datastore.DataStore;
import me.harry0198.infoheads.gui.WizardGui;
import me.harry0198.infoheads.inventory.Inventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public final class HeadPlace implements Listener {

    private final InfoHeads plugin;

    public HeadPlace(final InfoHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void placeHead(BlockPlaceEvent e) {
        if (!DataStore.placerMode.contains(e.getPlayer())) return;
        if (plugin.getDataStore().getDraft(e.getPlayer()) == null) return;

        plugin.getDataStore().getDraft(e.getPlayer()).setLocation(e.getBlockPlaced().getLocation());

        Inventory.restoreInventory(e.getPlayer());

        if (plugin.getDataStore().getOpenMenu(e.getPlayer()) == null)
            plugin.getDataStore().addOpenMenu(e.getPlayer(), new WizardGui(plugin, e.getPlayer()));

        DataStore.placerMode.remove(e.getPlayer());
        plugin.getDataStore().getOpenMenu(e.getPlayer()).open();
    }
}
