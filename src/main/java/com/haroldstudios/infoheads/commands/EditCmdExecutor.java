package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.utils.Constants;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The {@code EditCmdExecutor} class extends {@link CmdExecutor} to handle the
 * editing of InfoHeads. It ensures that the player can edit an InfoHead at
 * a specified location if it exists.
 */
public class EditCmdExecutor extends CmdExecutor {

    private final DataStore dataStore;

    /**
     * Constructs a new {@link EditCmdExecutor} with the specified {@link DataStore}.
     *
     * @param dataStore the {@link DataStore} instance used to manage InfoHeads data
     */
    public EditCmdExecutor(DataStore dataStore) {
        super(Constants.ADMIN_PERM);
        this.dataStore = dataStore;
    }

    /**
     * Executes the edit command for the given {@link CommandSender}. If the sender is
     * a player, it checks if the targeted block contains an InfoHead. If it does,
     * it proceeds to handle the editing logic.
     *
     * @param sender the {@link CommandSender} executing the command, expected to be a {@code Player}
     * @return {@code true} if the command was successfully executed, otherwise {@code false}
     */
    @Override
    public boolean executeCmd(CommandSender sender) {
        Player player = (Player) sender;
        Block b = player.getTargetBlock(null, 5);
        Location targetLoc = b.getLocation();

        if (targetLoc == null || !dataStore.getInfoHeads().containsKey(targetLoc)) {

            MessageUtil.sendMessage(player, MessageUtil.Message.NO_INFOHEAD_AT_LOC);
            return true;
        }

        InfoHeadConfiguration headAtLoc = dataStore.getInfoHeads().get(targetLoc);

//        new WizardGui(plugin, player, headAtLoc).open();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerOnlyCmd() {
        return false;
    }
}
