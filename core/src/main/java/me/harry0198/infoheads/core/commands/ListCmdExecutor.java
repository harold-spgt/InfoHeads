package me.harry0198.infoheads.core.commands;


import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.inputs.ShowInfoHeadListEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.Constants;

public class ListCmdExecutor extends CmdExecutor {

    private final EventDispatcher eventDispatcher;
    private final InfoHeadService infoHeadService;

    public ListCmdExecutor(LocalizedMessageService localizedMessageService, InfoHeadService infoHeadService, EventDispatcher eventDispatcher) {
        super(localizedMessageService, eventDispatcher, Constants.ADMIN_PERMISSION);
        this.infoHeadService = infoHeadService;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public boolean executeCmd(OnlinePlayer player) {
        this.eventDispatcher.dispatchEvent(new ShowInfoHeadListEvent(player, infoHeadService.getAll()));
        return true;
    }
}
