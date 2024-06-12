package me.harry0198.infoheads.core.commands;


import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.utils.Constants;

import java.util.concurrent.CompletableFuture;

public class ListCmdExecutor extends CmdExecutor {
    public ListCmdExecutor(LocalizedMessageService localizedMessageService) {
        super(localizedMessageService, Constants.ADMIN_PERMISSION);
    }

    @Override
    public boolean executeCmd(OnlinePlayer sender) {
        CompletableFuture.runAsync(() -> {
//            sender.sendMessage("§8+§m-------§8[§bIF List§8]§m-------§8+");
//            sender.sendMessage("§bClick an element to teleport to the head");

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
}
