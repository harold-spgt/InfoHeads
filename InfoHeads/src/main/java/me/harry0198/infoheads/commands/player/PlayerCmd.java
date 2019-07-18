package me.harry0198.infoheads.commands.player;

import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.inventorys.Inventory;
import me.harry0198.infoheads.utils.Constants;
import me.harry0198.infoheads.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

public class PlayerCmd implements CommandExecutor {

    private InfoHeads infoHeads;

    public PlayerCmd(InfoHeads infoHeads) {
        this.infoHeads = infoHeads;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Conversable)) return false;
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (infoHeads.getPerms().playerHas((Player) sender, Constants.ADMIN_PERM)) {
            switch (args.length) {
                case 0:
                    createCommand(sender);
                    break;
                case 1:
                    switch (args[0]) {
                        case "reload":
                            infoHeads.reloadCommand();
                            Utils.sendMessage(player, "&aInfoHeads: &fConfig reloaded!");
                            break;
                        case "delete":
                            if (infoHeads.deleteMode.get(player)) {
                                infoHeads.deleteMode.remove(player);
                                Utils.sendMessage(player, "&aInfoHeads: &fExit deletion mode");
                            } else {
                                infoHeads.deleteMode.put(player, true);
                                Utils.sendMessage(player, "&aInfoHeads: &fInteract with the block you wish to delete!");
                            }
                            break;
                        default:
                            Utils.sendMessage(player, "&aInfoHeads: &fThat is not a valid argument!");
                            break;
                    }
                    break;
                default:
                    Utils.sendMessage(player, "&aInfoHeads: &fThat is not a valid argument!");
                    break;
            }

        } else {
            sender.sendMessage(ChatColor.RED + "No permission");
        }
        return false;

    }

    private void createCommand(CommandSender sender) {

        Inventory iv = new Inventory(infoHeads);

        if (!(Bukkit.getVersion().contains("1.8"))) { // 1.8 clients does not support inventory storage
            iv.storeAndClearInventory((Player) sender);
        }
        iv.infoHeadsInventory((Player) sender);
        infoHeads.getConversationFactory().buildConversation((Conversable) sender).begin();
    }

}
