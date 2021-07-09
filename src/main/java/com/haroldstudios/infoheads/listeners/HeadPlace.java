package com.haroldstudios.infoheads.listeners;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.hooks.BlockParticlesHook;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.gui.WizardGui;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.awt.*;

public final class HeadPlace implements Listener {

    private final InfoHeads plugin;

    public HeadPlace(final InfoHeads plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void placeHead(BlockPlaceEvent e) {
        if (!DataStore.placerMode.containsKey(e.getPlayer())) return;

        InfoHeadConfiguration configuration = DataStore.placerMode.get(e.getPlayer());

        configuration.setLocation(e.getBlockPlaced().getLocation());

        InfoHeadConfiguration matched = InfoHeads.getInstance().getDataStore().getMatchingInfoHead(configuration);

        if (matched == null) {
            InfoHeads.getInstance().getDataStore().addInfoHead(configuration);
        }

        Location matchedLoc = InfoHeads.getInstance().getDataStore().getKeyByValue(matched);
        InfoHeads.getInstance().getDataStore().getInfoHeads().remove(matchedLoc);
        InfoHeads.getInstance().getDataStore().addInfoHead(configuration);
        if (plugin.blockParticles && configuration.getParticle() != null) {
            BlockParticlesHook.newLoc(e.getPlayer(), configuration.getId().toString(), configuration.getParticle());
        }

        new WizardGui(plugin, e.getPlayer(), configuration).open();

        DataStore.placerMode.remove(e.getPlayer());
    }
}
