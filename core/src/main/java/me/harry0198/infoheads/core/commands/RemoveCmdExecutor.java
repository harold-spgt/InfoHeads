package me.harry0198.infoheads.core.commands;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.Constants;

import java.util.Optional;

/**
 * The {@code RemoveCmdExecutor} class extends {@link CmdExecutor} to execute a command
 * for removing InfoHeads. It handles the command execution logic to ensure that
 * InfoHeads can be removed from specific locations.
 */
public class RemoveCmdExecutor extends CmdExecutor {
    private final InfoHeadService infoHeadService;
    private final EventDispatcher eventDispatcher;

    /**
     * Class constructor.
     *
     * @param messageService Messages service.
     * @param infoHeadService the {@link InfoHeadService} instance used to manage InfoHeads
     */
    @Inject
    public RemoveCmdExecutor(MessageService messageService, InfoHeadService infoHeadService, EventDispatcher eventDispatcher) {
        super(messageService, eventDispatcher, Constants.ADMIN_PERMISSION);
        this.infoHeadService = infoHeadService;
        this.eventDispatcher = eventDispatcher;
    }

    /**
     * Executes the remove command for the given {@link Player}. If the sender is
     * a player and is targeting a block that contains an InfoHead, the InfoHead is removed
     * from the specified location. Otherwise, an appropriate message is sent to the player.
     *
     * @param sender the {@link OnlinePlayer} executing the command, expected to be a {@code Player}
     * @return {@code true} if the command was successfully executed, otherwise {@code false}
     */
    @SuppressWarnings("squid:S3516")
    @Override
    public boolean executeCmd(Command command, OnlinePlayer sender) {
        Optional<InfoHeadProperties> infoHeadPropertiesOptional = infoHeadService.getInfoHead(sender.getLookingAt().orElse(null));

        if (infoHeadPropertiesOptional.isEmpty()) {
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(
                    sender,
                    getLocalizedMessageService().getMessage(BundleMessages.NO_INFOHEAD_AT_LOCATION)));
            return true;
        }

        infoHeadService.removeInfoHead(infoHeadPropertiesOptional.get());
        eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(sender, getLocalizedMessageService().getMessage(BundleMessages.INFOHEAD_REMOVED)));

        return true;
    }
}
