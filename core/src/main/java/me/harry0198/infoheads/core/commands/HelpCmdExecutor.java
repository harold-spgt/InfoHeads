package me.harry0198.infoheads.core.commands;


import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.utils.Constants;

/**
 * Executor for the help command, provides a help message
 * to the sender upon execution.
 */
public class HelpCmdExecutor extends CmdExecutor {

    /**
     * {@inheritDoc}
     */
    public HelpCmdExecutor(MessageService messageService, EventDispatcher eventDispatcher) {
        super(messageService,eventDispatcher,Constants.BASE_PERMISSION + "help");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean executeCmd(OnlinePlayer sender) {
        getEventDispatcher().dispatchEvent(new SendPlayerMessageEvent(sender, getLocalizedMessageService().getMessage(BundleMessages.HELP)));
        return true;
    }
}
