package me.harry0198.infoheads.core.commands;


import com.google.inject.Inject;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.ShowInfoHeadListEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.Constants;

public class ListCmdExecutor extends CmdExecutor {

    private final EventDispatcher eventDispatcher;
    private final InfoHeadService infoHeadService;

    @Inject
    public ListCmdExecutor(MessageService messageService, InfoHeadService infoHeadService, EventDispatcher eventDispatcher) {
        super(messageService, eventDispatcher, Constants.ADMIN_PERMISSION);
        this.infoHeadService = infoHeadService;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean executeCmd(Command command, OnlinePlayer player) {
        this.eventDispatcher.dispatchEvent(new ShowInfoHeadListEvent(player, infoHeadService.getAll()));
        return true;
    }
}
