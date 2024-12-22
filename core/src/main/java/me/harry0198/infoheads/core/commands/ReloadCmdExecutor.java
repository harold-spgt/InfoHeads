package me.harry0198.infoheads.core.commands;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.Plugin;
import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.utils.Constants;

public class ReloadCmdExecutor extends CmdExecutor {

    private final Plugin infoHeadsPlugin;

    @Inject
    public ReloadCmdExecutor(Plugin infoHeadsPlugin, MessageService messageService, EventDispatcher eventDispatcher) {
        super(messageService, eventDispatcher, Constants.ADMIN_PERMISSION);
        this.infoHeadsPlugin = infoHeadsPlugin;
    }

    @Override
    public boolean executeCmd(Command command, OnlinePlayer sender) {
        // This must be before the reload because a reload unregisters the event handlers so the player will never
        // get the message! Agree this is incorrect but the message is descriptive enough to indicate that it's not
        // a successful reload - just that one has been executed / requested. This could be resolved but the impact /
        // benefit is very small and not worth the time at the moment.
        getEventDispatcher().dispatchEvent(new SendPlayerMessageEvent(sender, getLocalizedMessageService().getMessage(BundleMessages.RELOAD)));
        infoHeadsPlugin.reload();
        return true;
    }
}
