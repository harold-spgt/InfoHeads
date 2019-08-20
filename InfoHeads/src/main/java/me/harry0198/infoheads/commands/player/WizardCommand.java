package me.harry0198.infoheads.commands.player;

import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.commands.Command;
import me.harry0198.infoheads.inventorys.Inventory;
import me.harry0198.infoheads.utils.Constants;
import me.harry0198.infoheads.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

public class WizardCommand extends Command {

    public WizardCommand() {
        super("wizard", "Setup Wizard.", "", Constants.ADMIN_PERM);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {

        Utils.sendMessage(sender, "&lYou are in wizard mode, type 'cancel' to exit at any time.");


        if (!(Bukkit.getVersion().contains("1.8"))) { // 1.8 clients does not support inventory storage
            Inventory.storeAndClearInventory((Player) sender);
        }
        Bukkit.getScheduler().runTaskAsynchronously(getInstance(), () ->
                new Inventory().infoHeadsInventory((Player) sender));

        getInstance().getConversationFactory().buildConversation((Conversable) sender).begin();
        return true;
    }

    private InfoHeads getInstance() {
        return InfoHeads.getInstance();
    }
}
