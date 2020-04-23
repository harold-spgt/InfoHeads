package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.elements.*;
import com.haroldstudios.infoheads.gui.WizardGui;
import com.haroldstudios.infoheads.inventory.HeadStacks;
import com.haroldstudios.infoheads.utils.Constants;
import com.haroldstudios.infoheads.utils.MessageUtil;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @SubCommand("convert")
    public void convert(CommandSender sender) {

        ConfigurationSection root = plugin.getConfig().getConfigurationSection("Infoheads");
        if (root == null || root.getKeys(false) == null) {
            sender.sendMessage("InfoHeads cannot find any infoheads in your config.yml!");
            return;
        }

        Map<Location, InfoHeadConfiguration> infoHeads = new HashMap<>();

        for (String key : root.getKeys(false)) {

            ConfigurationSection locRoot = root.getConfigurationSection(key + ".location");
            if (locRoot == null) continue;

            Location location = new Location(Bukkit.getWorld(locRoot.getString("world")), locRoot.getLong("x"), locRoot.getLong("y"), locRoot.getLong("z"));
            String permission = root.getString(key + "permission");

            ConfigurationSection elementRoot = root.getConfigurationSection(key + ".elements");
            List<Element> elementList = new ArrayList<>();
            if (elementRoot != null)
                for (String k : elementRoot.getKeys(false)) {
                    Element.InfoHeadType type;
                    try {
                        type = Element.InfoHeadType.valueOf(elementRoot.getString(k + ".type"));
                    } catch (IllegalArgumentException args) {
                        plugin.warn("Invalid Type of value " + k);
                        continue;
                    }
                    switch (type) {
                        case MESSAGE:
                            elementList.add(new MessageElement(elementRoot.getString(k + ".content")));
                            break;
                        case CONSOLE_COMMAND:
                            elementList.add(new ConsoleCommandElement(elementRoot.getString(k + ".content")));
                            break;
                        case PLAYER_COMMAND:
                            elementList.add(new PlayerCommandElement(elementRoot.getString(k + ".content")));
                            break;
                        case DELAY:
                            elementList.add(new DelayElement(elementRoot.getInt(k + ".content")));
                            break;
                    }
                }

            InfoHeadConfiguration configuration = new InfoHeadConfiguration();
            configuration.setLocation(location);
            configuration.setElementList(elementList);
            configuration.setPermission(permission);

            infoHeads.put(location, configuration);
        }

        plugin.getConfig().set("Infoheads", null);

        plugin.getDataStore().forceSetInfoHeads(infoHeads);

        sender.sendMessage("Config has been converted to the new format! Your infoheads can now be found in datastore.json. Be careful to only change what you know!");
    }
}
