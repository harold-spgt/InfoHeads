package me.harry0198.infoheads.commands.player;

import com.google.inject.Inject;
import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.commands.Command;
import me.harry0198.infoheads.commands.CommandManager;
import me.harry0198.infoheads.listeners.EntityListeners;
import me.harry0198.infoheads.utils.Constants;
import org.bukkit.command.CommandSender;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ReloadCommand extends Command {
    @Inject private InfoHeads infoHeads;
    @Inject private CommandManager commandManager;

    public ReloadCommand() {
        super("reload", "Reloads the config.", "", Constants.ADMIN_PERM);
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {

        infoHeads.reloadConfig();

        infoHeads.register(InfoHeads.Registerables.INFOHEADS);
        BlockPlaceEvent.getHandlerList().unregister(infoHeads);
        PlayerInteractEvent.getHandlerList().unregister(infoHeads);
        infoHeads.getServer().getPluginManager().registerEvents(new EntityListeners(infoHeads, infoHeads.offHand, commandManager), infoHeads);

        return true;
    }
}
