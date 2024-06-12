package me.harry0198.infoheads.core.commands;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.model.PlayerDetailSnapshot;
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
     * Executes the wizard command for the given {@link PlayerDetailSnapshot}. If the sender is
     * a player and not already conversing or in placer mode, it sets up the player
     * for placing an InfoHead by sending a message, creating an {@link InfoHeadProperties},
     * and giving the player necessary items. Placer mode is then picked up by block place events.
     *
     * @param sender the {@link PlayerDetailSnapshot} of the player executing the command.
     * @return {@code true} if the command was successfully executed, otherwise {@code false}
     */
    @Override
    public boolean executeCmd(PlayerDetailSnapshot sender) {
//        if (DataStore.placerMode.containsKey(player)) return true;
        getLocalizedMessageService().getNotificationStrategy().send(sender, getLocalizedMessageService().getMessage(BundleMessages.USER_SHOULD_PLACE_INFOHEAD));
//        DataStore.placerMode.put(player, infoHeadConfiguration);
//
//        HeadStacks.giveHeads(player);
        return true;
    }
}
