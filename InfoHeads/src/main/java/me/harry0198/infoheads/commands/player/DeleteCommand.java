package me.harry0198.infoheads.commands.player;

import com.google.inject.Inject;
import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.commands.Command;
import me.harry0198.infoheads.utils.Constants;
import me.harry0198.infoheads.utils.LoadedLocations;
import me.harry0198.infoheads.utils.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.harry0198.infoheads.InfoHeads.getInstance;

public class DeleteCommand extends Command {
    @Inject private InfoHeads infoHeads;

    public DeleteCommand() {
        super("delete", "Deletes an InfoHead.", "", Constants.ADMIN_PERM);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Block b = player.getTargetBlock(null, 5);
        Location targetLoc = b.getLocation();
        if (!checkLocationExists(targetLoc)) {
            Utils.sendMessage(player, "There is no infohead at this location.");
            return true; }
        deleteInfoHead(targetLoc);
        Utils.sendMessage(player, "InfoHead successfully deleted");

        return true;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean checkLocationExists(Location location) {
        for (LoadedLocations loc : getInstance().getLoadedLoc()) {
            if (location.equals(loc.getLocation())) return true;
        }
        return false;
    }

    public void deleteInfoHead(Location location) {
        for (LoadedLocations loc : getInstance().getLoadedLoc()) {
            if (location.equals(loc.getLocation())) {
                getInstance().getConfig().set("Infoheads." + loc.getKey(), null);
                getInstance().saveConfig();
                infoHeads.register(InfoHeads.Registerables.INFOHEADS);
                return;
            }
        }
    }
}
