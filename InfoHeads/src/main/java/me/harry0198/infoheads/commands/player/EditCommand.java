package me.harry0198.infoheads.commands.player;

import com.google.inject.Inject;
import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.commands.Command;
import me.harry0198.infoheads.utils.Constants;
import me.harry0198.infoheads.utils.LoadedLocations;
import me.harry0198.infoheads.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;
    //todo block
public class EditCommand extends Command {
    @Inject private InfoHeads infoHeads;

    public EditCommand() {
        super("edit", "Edit an infohead you're looking at.", "[cmd/msg]", Constants.ADMIN_PERM);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {//TODO getcommand
            Utils.sendMessage(player, "Incorrect Syntax! /Infoheads edit [cmd/msg/block]");
            return true;
        }
        Types type;

        switch (args[0].toLowerCase()) {
            case "cmd":
                type = Types.COMMANDS;
                break;

            case "msg":
                type = Types.MESSAGES;
                break;

            /*case "block":
                type = Types.BLOCK;

                break;*/

            default:
                Utils.sendMessage(player, "Incorrect Syntax! /Infoheads edit [cmd/msg");
                return true;
        }

        Block b = player.getTargetBlock(null, 5);
        Location targetLoc = b.getLocation();

        if (!checkLocationExists(targetLoc)) { //TODO message in method
            Utils.sendMessage(player, "There is no infohead at this location.");
            return true; }

        LoadedLocations headAtLoc = getHeadAtLoc(targetLoc);
        Utils.sendMessage(player, getStrings(headAtLoc, type));
        infoHeads.getCurrentEditType().put(player, type);
        infoHeads.typesMapClass.put(player, headAtLoc);

        infoHeads.getEditFactory().buildConversation((Conversable) sender).begin();

        return true;
    }

    private boolean checkLocationExists(Location location) {
        for (LoadedLocations loc : infoHeads.getLoadedLoc()) {
            if (location.equals(loc.getLocation())) return true;
        }
        return false;
    }

    private LoadedLocations getHeadAtLoc(Location location) {
        for (LoadedLocations loc : infoHeads.getLoadedLoc()) {
            if (location.equals(loc.getLocation())) {
                return loc;
            }
        }
        return null;
    }

    /**
     * Returns the values defined ex: messages / commands
     *
     * @param loc LoadedLocations instance
     * @return List of Cmds / Msgs
     */
    private String getStrings(LoadedLocations loc, Types type) {
        if (type.equals(Types.COMMANDS)) {
            StringBuilder builder = new StringBuilder();
            int i = 0;
            for (String each : loc.getCommands()) {
                builder.append(i + ") " + ChatColor.translateAlternateColorCodes('&', each) + "\n");
                i++;
            }
            return builder.toString();
        } else {
            StringBuilder builder = new StringBuilder();
            int i = 0;
            for (String each : loc.getMessages()) {
                builder.append(i + ") " + ChatColor.translateAlternateColorCodes('&', each) + "\n");
                i++;
            }
            return builder.toString();
        }
    }

    public enum Types {
        MESSAGES, COMMANDS, BLOCK
    }


}
