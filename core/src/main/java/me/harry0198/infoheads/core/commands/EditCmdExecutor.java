package me.harry0198.infoheads.core.commands;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.event.types.OpenMenuMenuEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.Constants;

import java.util.Optional;

/**
 * The {@code EditCmdExecutor} class extends {@link CmdExecutor} to handle the
 * editing of InfoHeads. It ensures that the player can edit an InfoHead at
 * a specified location if it exists.
 */
public class EditCmdExecutor extends CmdExecutor {

    private final InfoHeadService infoHeadService;
    private final EventDispatcher eventDispatcher;

    /**
     * Class constructor.
     * @param infoHeadService the {@link InfoHeadService} instance used to manage InfoHeads data.
     */
    @Inject
    public EditCmdExecutor(MessageService messageService, InfoHeadService infoHeadService, EventDispatcher eventDispatcher) {
        super(messageService, eventDispatcher, Constants.ADMIN_PERMISSION);
        this.infoHeadService = infoHeadService;
        this.eventDispatcher = eventDispatcher;
    }

    /**
     * Executes the edit command for the given {@link Player}. If the sender is
     * a player, it checks if the targeted block contains an InfoHead. If it does,
     * it proceeds to handle the editing logic.
     *
     * @param player the {@link OnlinePlayer} executing the command.
     * @return {@code true} if the command was successfully executed, otherwise {@code false}
     */
    @SuppressWarnings("squid:S3516")
    @Override
    public boolean executeCmd(Command command, OnlinePlayer player) {
        Optional<InfoHeadProperties> infoHeadPropertiesOptional = infoHeadService.getInfoHead(player.getLookingAt().orElse(null));
        if (infoHeadPropertiesOptional.isEmpty()) {
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(player, getLocalizedMessageService().getMessage(BundleMessages.NO_INFOHEAD_AT_LOCATION)));
            return true;
        }

        eventDispatcher.dispatchEvent(new OpenMenuMenuEvent(infoHeadPropertiesOptional.get(), player));
        return true;
    }
}
