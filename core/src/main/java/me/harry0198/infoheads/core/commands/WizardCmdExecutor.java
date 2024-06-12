package me.harry0198.infoheads.core.commands;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.utils.Constants;

/**
 * The {@code WizardCmdExecutor} class extends {@link CmdExecutor} to execute a specific
 * command related to the wizard functionality. It puts the player into "placerMode" which
 * is then picked up by block place events.
 */
public class WizardCmdExecutor extends CmdExecutor {

    /**
     * {@inheritDoc}
     */
    public WizardCmdExecutor(LocalizedMessageService localizedMessageService) {
        super(localizedMessageService, Constants.BASE_PERMISSION + "wizard");
    }

    /**
     * Executes the wizard command for the given {@link OnlinePlayer}. If the sender is
     * a player and not already conversing or in placer mode, it sets up the player
     * for placing an InfoHead by sending a message, creating an {@link InfoHeadProperties},
     * and giving the player necessary items. Placer mode is then picked up by block place events.
     *
     * @param sender the {@link OnlinePlayer} of the player executing the command.
     * @return {@code true} if the command was successfully executed, otherwise {@code false}
     */
    @Override
    public boolean executeCmd(OnlinePlayer sender) {
//        if (DataStore.placerMode.containsKey(player)) return true;
        sender.sendMessage(getLocalizedMessageService().getMessage(BundleMessages.USER_SHOULD_PLACE_INFOHEAD));
//        DataStore.placerMode.put(player, infoHeadConfiguration);
//
//        HeadStacks.giveHeads(player);
        return true;
    }
}
