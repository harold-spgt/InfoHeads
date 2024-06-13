package me.harry0198.infoheads.core.eventhandler;

import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;

public class PlaceHandler {

    public void placeHead(OnlinePlayer player, Location location) {
//        if (!DataStore.placerMode.containsKey(e.getPlayer())) return;
//
//        InfoHeadConfiguration configuration = DataStore.placerMode.get(e.getPlayer());
//
//        configuration.setLocation(e.getBlockPlaced().getLocation());
//
//        InfoHeadConfiguration matched = InfoHeads.getInstance().getDataStore().getMatchingInfoHead(configuration);
//
//        if (matched == null) {
//            InfoHeads.getInstance().getDataStore().addInfoHead(configuration);
//        }
//
//        Location matchedLoc = InfoHeads.getInstance().getDataStore().getKeyByValue(matched);
//        InfoHeads.getInstance().getDataStore().getInfoHeads().remove(matchedLoc);
//        InfoHeads.getInstance().getDataStore().addInfoHead(configuration);
//        if (plugin.blockParticles && configuration.getParticle() != null) {
//            BlockParticlesHook.newLoc(e.getPlayer(), configuration.getId().toString(), configuration.getParticle());
//        }
//
//        new WizardGui(new WizardViewModel(plugin, configuration)).open(e.getPlayer());
//
//        DataStore.placerMode.remove(e.getPlayer());
    }
}
