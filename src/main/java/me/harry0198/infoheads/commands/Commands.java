package me.harry0198.infoheads.commands;

import me.harry0198.infoheads.InfoHeadConfiguration;
import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.datastore.DataStore;
import me.harry0198.infoheads.inventory.Inventory;
import me.harry0198.infoheads.utils.Constants;
import me.harry0198.infoheads.utils.MessageUtil;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
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

    @Permission(Constants.BASE_PERM + "help")
    @SubCommand("help")
    public void help(Player player) {
        player.sendMessage(MessageUtil.HELP);
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("wizard")
    public void wizard(Player player) {
        if (player.isConversing()) return;
        new InfoHeadConfiguration.Draft(player);
        startWiz(player);
    }

    public void startWiz(Player player) {
        if (DataStore.placerMode.contains(player)) return;
        MessageUtil.sendMessage(player, MessageUtil.PLACE_INFOHEAD);
        DataStore.placerMode.add(player);
        Inventory.giveHeads(plugin, player);
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

        InfoHeadConfiguration head = plugin.getDataStore().getInfoHeads().get(targetLoc);
        plugin.getConfig().set("Infoheads." + head.getKey(), null);
        plugin.saveConfig();
        plugin.getDataStore().getInfoHeads().remove(targetLoc);
        MessageUtil.sendMessage(player, MessageUtil.INFOHEAD_REMOVED);
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("reload")
    public void reload(Player player) {
        BlockPlaceEvent.getHandlerList().unregister(plugin);
        PlayerInteractEvent.getHandlerList().unregister(plugin);
        plugin.load();
        MessageUtil.sendMessage(player, MessageUtil.RELOAD);
    }

}
