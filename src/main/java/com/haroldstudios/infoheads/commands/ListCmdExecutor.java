package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.utils.Constants;
import org.bukkit.command.CommandSender;

import java.util.concurrent.CompletableFuture;

public class ListCmdExecutor extends CmdExecutor {
    public ListCmdExecutor() {
        super(Constants.ADMIN_PERM);
    }

    @Override
    public boolean executeCmd(CommandSender sender) {
        CompletableFuture.runAsync(() -> {
            sender.sendMessage("§8+§m-------§8[§bIF List§8]§m-------§8+");
            sender.sendMessage("§bClick an element to teleport to the head");

//            for (InfoHeadConfiguration head : plugin.getDataStore().getInfoHeads().values()) {
//                Location loc = head.getLocation();
//                String locString = String.format("§b%s %s %s", loc.getX(), loc.getY(), loc.getZ());
//                String name = head.getName() != null ? "§8" + head.getName() + " §7- " + locString : locString;
//
////                Component component = Component.text(name)
////                        .hoverEvent(HoverEvent.showText(Component.text("§8+§m---§r §bClick to teleport §8+§m---§r")))
////                        .clickEvent(ClickEvent.runCommand("/tp " + loc.getX() + " " + loc.getY() + " " + loc.getZ()));
////
////                InfoHeads.getAdventure().player(player).sendMessage(component);
//            }
//
//            player.sendMessage("§8+§m-------§8[§bIF List§8]§m-------§8+");

        });
        return true;
    }

    @Override
    public boolean isPlayerOnlyCmd() {
        return true;
    }
}
