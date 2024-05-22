package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.utils.Constants;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The {@code RemoveCmdExecutor} class extends {@link CmdExecutor} to execute a command
 * for removing InfoHeads. It handles the command execution logic to ensure that
 * InfoHeads can be removed from specific locations.
 */
public class RemoveCmdExecutor extends CmdExecutor {
    private final DataStore dataStore;

    /**
     * Constructs a new {@code RemoveCmdExecutor} with the specified {@code DataStore}.
     *
     * @param dataStore the {@link DataStore} instance used to manage InfoHeads
     */
    public RemoveCmdExecutor(DataStore dataStore) {
        super(Constants.ADMIN_PERM);
        this.dataStore = dataStore;
    }

    /**
     * Executes the remove command for the given {@link CommandSender}. If the sender is
     * a player and is targeting a block that contains an InfoHead, the InfoHead is removed
     * from the specified location. Otherwise, an appropriate message is sent to the player.
     *
     * @param sender the {@link CommandSender} executing the command, expected to be a {@code Player}
     * @return {@code true} if the command was successfully executed, otherwise {@code false}
     */
    @Override
    public boolean executeCmd(CommandSender sender) {
        Player player = (Player) sender;
        Block b = player.getTargetBlock(null, 5);
        Location targetLoc = b.getLocation();
        if (!dataStore.getInfoHeads().containsKey(targetLoc)) {
            sender.sendMessage(MessageUtil.getString(MessageUtil.Message.NO_INFOHEAD_AT_LOC));
            return true;
        }

        dataStore.removeInfoHeadAt(targetLoc);
        sender.sendMessage(MessageUtil.getString(MessageUtil.Message.INFOHEAD_REMOVED));

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerOnlyCmd() {
        return true;
    }
}
