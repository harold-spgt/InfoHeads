package me.harry0198.infoheads.commands.player;

import com.google.common.collect.Lists;
import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.inventorys.Inventory;
import me.harry0198.infoheads.utils.Constants;
import me.harry0198.infoheads.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PlayerCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Conversable)) return false;
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (InfoHeads.getPerms().playerHas((Player) sender, Constants.ADMIN_PERM)) {
            switch (args.length) {
                case 0:
                   Utils.showHelp(player);
                    break;
                case 1:
                    switch (args[0]) {
                        case "wizard":
                            Utils.sendMessage(player, "&lYou are in wizard mode, type 'cancel' to exit at any time.");
                            createCommand(sender);
                            break;
                        case "reload":
                            getInstance().reloadCommand();
                            Utils.sendMessage(player, "&fConfig reloaded!");
                            break;
                        case "delete":
                            Block b = player.getTargetBlock(null, 5);
                            Location targetLoc = b.getLocation();
                            if (!getInstance().checkLocationExists(targetLoc, player)) {
                                Utils.sendMessage(player, "There is no infohead at this location.");
                                break; }
                            getInstance().deleteInfoHead(targetLoc);
                            Utils.sendMessage(player, "InfoHead successfully deleted");
                            break;
                        case "help":
                            Utils.showHelp(player);
                            break;
                        default:
                            Utils.sendMessage(player, "&fThat is not a valid argument!");
                            break;
                    }
                    break;
                default:
                    Utils.sendMessage(player, "&fThat is not a valid argument!");
                    break;
            }

        } else {
            sender.sendMessage(ChatColor.RED + "No permission");
        }
        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> a1 = Arrays.asList("reload", "wizard", "help", "delete");
        List<String> fList = Lists.newArrayList();

        switch (args.length) {
            case 1:
                for (String each : a1) {
                    if (each.toLowerCase().startsWith(args[0].toLowerCase())) {
                        fList.add(each);
                    }
                }
                return fList;
        }
        return null;
    }

    private void createCommand(CommandSender sender) {

        Inventory iv = new Inventory();

        if (!(Bukkit.getVersion().contains("1.8"))) { // 1.8 clients does not support inventory storage
            iv.storeAndClearInventory((Player) sender);
        }
        Bukkit.getScheduler().runTaskAsynchronously(getInstance(), () ->
            iv.infoHeadsInventory((Player) sender));

        getInstance().getConversationFactory().buildConversation((Conversable) sender).begin();
    }

    private InfoHeads getInstance() {
        return InfoHeads.getInstance();
    }

}
