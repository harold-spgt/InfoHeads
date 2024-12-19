package me.harry0198.infoheads.core.commands;


import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;

/**
 * Command executor for when an unknown subcommand is
 * passed. Provides a helpful message to help users.
 */
public class UnknownCmdExecutor extends CmdExecutor {

    /**
     * {@inheritDoc}
     */
    public UnknownCmdExecutor(MessageService messageService, EventDispatcher eventDispatcher) {
        super(messageService, eventDispatcher, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean executeCmd(OnlinePlayer sender) {
        getEventDispatcher().dispatchEvent(new SendPlayerMessageEvent(sender, getLocalizedMessageService().getMessage(BundleMessages.UNKNOWN_CMD)));
        return true;
    }
}
