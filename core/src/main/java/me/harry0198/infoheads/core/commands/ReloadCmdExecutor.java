package me.harry0198.infoheads.core.commands;

import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.utils.Constants;

public class ReloadCmdExecutor extends CmdExecutor {

    private final InfoHeadsPlugin infoHeadsPlugin;

    public ReloadCmdExecutor(InfoHeadsPlugin infoHeadsPlugin, MessageService messageService, EventDispatcher eventDispatcher) {
        super(messageService, eventDispatcher, Constants.ADMIN_PERMISSION);
        this.infoHeadsPlugin = infoHeadsPlugin;
    }

    @Override
    public boolean executeCmd(OnlinePlayer sender) {
        infoHeadsPlugin.reload();
        getEventDispatcher().dispatchEvent(new SendPlayerMessageEvent(sender, getLocalizedMessageService().getMessage(BundleMessages.RELOAD)));
        return true;
    }
}
