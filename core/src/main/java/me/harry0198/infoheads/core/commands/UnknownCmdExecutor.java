package me.harry0198.infoheads.core.commands;


import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.PlayerDetailSnapshot;

/**
 * Command executor for when an unknown subcommand is
 * passed. Provides a helpful message to help users.
 */
public class UnknownCmdExecutor extends CmdExecutor {

    /**
     * {@inheritDoc}
     */
    public UnknownCmdExecutor(LocalizedMessageService localizedMessageService) {
        super(localizedMessageService, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean executeCmd(PlayerDetailSnapshot sender) {
        getLocalizedMessageService().getNotificationStrategy().send(sender, getLocalizedMessageService().getMessage(BundleMessages.UNKNOWN_CMD));
        return true;
    }
}
