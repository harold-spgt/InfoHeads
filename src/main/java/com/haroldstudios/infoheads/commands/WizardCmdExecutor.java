package com.haroldstudios.infoheads.commands;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.inventory.HeadStacks;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The {@code WizardCmdExecutor} class extends {@link CmdExecutor} to execute a specific
 * command related to the wizard functionality. It puts the player into "placerMode" which
 * is then picked up by block place events.
 */
public class WizardCmdExecutor extends CmdExecutor {

    /**
     * {@inheritDoc}
     */
    public WizardCmdExecutor() {
        super("infoheads.wizard");
    }

    /**
     * Executes the wizard command for the given {@link CommandSender}. If the sender is
     * a player and not already conversing or in placer mode, it sets up the player
     * for placing an InfoHead by sending a message, creating an {@link InfoHeadConfiguration},
     * and giving the player necessary items. Placer mode is then picked up by block place events.
     *
     * @param sender the {@link CommandSender} executing the command, expected to be a {@code Player}
     * @return {@code true} if the command was successfully executed, otherwise {@code false}
     */
    @Override
    public boolean executeCmd(CommandSender sender) {
        Player player = (Player) sender;
        if (player.isConversing()) return true;

        if (DataStore.placerMode.containsKey(player)) return true;
        player.sendMessage(MessageUtil.getString(MessageUtil.Message.PLACE_INFOHEAD));
        InfoHeadConfiguration infoHeadConfiguration = new InfoHeadConfiguration();
        DataStore.placerMode.put(player, infoHeadConfiguration);

        HeadStacks.giveHeads(player);
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
