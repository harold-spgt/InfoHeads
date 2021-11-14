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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
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
        MessageUtil.sendMessage(player, MessageUtil.Message.PLACE_INFOHEAD);
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
            MessageUtil.sendMessage(player, MessageUtil.Message.NO_INFOHEAD_AT_LOC);
            return;
        }

        plugin.getDataStore().removeInfoHeadAt(targetLoc);
        MessageUtil.sendMessage(player, MessageUtil.Message.INFOHEAD_REMOVED);
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("reload")
    public void reload(Player player) {
        plugin.getFileUtil().save(plugin.getDataStore());
        BlockPlaceEvent.getHandlerList().unregister(plugin);
        PlayerInteractEvent.getHandlerList().unregister(plugin);
        plugin.load();
        MessageUtil.sendMessage(player, MessageUtil.Message.RELOAD);
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("edit")
    public void edit(Player player) {

        Block b = player.getTargetBlock(null, 5);
        Location targetLoc = b.getLocation();


        if (targetLoc == null || !plugin.getDataStore().getInfoHeads().containsKey(targetLoc)) {
            MessageUtil.sendMessage(player, MessageUtil.Message.NO_INFOHEAD_AT_LOC);
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
            MessageUtil.sendMessage(player, MessageUtil.Message.NO_INFOHEAD_AT_LOC);
            return;
        }

        InfoHeadConfiguration configuration = new InfoHeadConfiguration();
        configuration.setLocation(targetLoc);
        InfoHeads.getInstance().getDataStore().addInfoHead(configuration);

        if (plugin.blockParticles && configuration.getParticle() != null) {
            BlockParticlesHook.newLoc(player, configuration.getId().toString(), configuration.getParticle());
        }

        new WizardGui(plugin, player, configuration).open();
    }

    @Permission(Constants.ADMIN_PERM)
    @SubCommand("list")
    public void list(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            player.sendMessage("§8+§m-------§8[§bIF List§8]§m-------§8+");
            player.sendMessage("§bClick an element to teleport to the head");

            for (InfoHeadConfiguration head : plugin.getDataStore().getInfoHeads().values()) {
                Location loc = head.getLocation();
                String locString = String.format("§b%s %s %s", loc.getX(), loc.getY(), loc.getZ());
                String name = head.getName() != null ? "§8" + head.getName() + " §7- " + locString : locString;

                Component component = Component.text(name)
                        .hoverEvent(HoverEvent.showText(Component.text("§8+§m---§r §bClick to teleport §8+§m---§r")))
                        .clickEvent(ClickEvent.runCommand("/tp " + loc.getX() + " " + loc.getY() + " " + loc.getZ()));

                InfoHeads.getAdventure().player(player).sendMessage(component);
            }

            player.sendMessage("§8+§m-------§8[§bIF List§8]§m-------§8+");

        });
    }

}
