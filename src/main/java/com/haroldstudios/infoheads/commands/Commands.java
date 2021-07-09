package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.gui.WizardGui;
import com.haroldstudios.infoheads.hooks.BlockParticlesHook;
import com.haroldstudios.infoheads.inventory.HeadStacks;
import com.haroldstudios.infoheads.utils.Constants;
import com.haroldstudios.infoheads.utils.MessageUtil;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

@Command("infoheads")
@Alias("if")
@SuppressWarnings("unused")
public final class Commands extends CommandBase {

    private final InfoHeads plugin;

    public Commands(InfoHeads plugin) {
        this.plugin = plugin;
    }

    @Default
    @Permission(Constants.BASE_PERM + "help")
    public void onDefaultCmd(Player player) {
        help(player);
    }

    @Permission(Constants.BASE_PERM + "help")
    @SubCommand("help")
    public void help(Player player) {
        player.sendMessage(MessageUtil.HELP);
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("wizard")
    public void wizard(Player player) {
        if (player.isConversing()) return;
        startWiz(player);
    }

    public void startWiz(Player player) {
        if (DataStore.placerMode.containsKey(player)) return;
        MessageUtil.sendMessage(player, MessageUtil.PLACE_INFOHEAD);
        InfoHeadConfiguration infoHeadConfiguration = new InfoHeadConfiguration();
        DataStore.placerMode.put(player, infoHeadConfiguration);

        HeadStacks.giveHeads(plugin, player);
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("remove")
    public void remove(Player player) {
        Block b = player.getTargetBlock(null, 5);
        Location targetLoc = b.getLocation();
        if (!plugin.getDataStore().getInfoHeads().containsKey(targetLoc)) {
            MessageUtil.sendMessage(player, MessageUtil.NO_INFOHEAD_AT_LOC);
            return;
        }

        plugin.getDataStore().removeInfoHeadAt(targetLoc);
        MessageUtil.sendMessage(player, MessageUtil.INFOHEAD_REMOVED);
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("reload")
    public void reload(Player player) {
        plugin.getFileUtil().save(plugin.getDataStore());
        BlockPlaceEvent.getHandlerList().unregister(plugin);
        PlayerInteractEvent.getHandlerList().unregister(plugin);
        plugin.load();
        MessageUtil.sendMessage(player, MessageUtil.RELOAD);
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("edit")
    public void edit(Player player) {

        Block b = player.getTargetBlock(null, 5);
        Location targetLoc = b.getLocation();


        if (targetLoc == null || !plugin.getDataStore().getInfoHeads().containsKey(targetLoc)) {
            player.sendMessage(MessageUtil.NO_INFOHEAD_AT_LOC);
            return;
        }

        InfoHeadConfiguration headAtLoc = plugin.getDataStore().getInfoHeads().get(targetLoc);

        new WizardGui(plugin, player, headAtLoc).open();
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("create")
    public void create(Player player) {
        Block b = player.getTargetBlock(null, 5);
        Location targetLoc = b.getLocation();
        if (plugin.getDataStore().getInfoHeads().containsKey(targetLoc)) {
            MessageUtil.sendMessage(player, MessageUtil.ALREADY_INFOHEAD);
            return;
        }

        InfoHeadConfiguration configuration = new InfoHeadConfiguration();
        configuration.setLocation(targetLoc);
        InfoHeads.getInstance().getDataStore().addInfoHead(configuration);

        if (plugin.blockParticles) {
            if (configuration.getParticle() != null) {
                BlockParticlesHook.newLoc(player, configuration.getId().toString(), configuration.getParticle());
            }
        }

        new WizardGui(plugin, player, configuration).open();
    }


}
