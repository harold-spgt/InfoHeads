package me.harry0198.infoheads.core.commands;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.actions.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.event.inputs.OpenInfoHeadMenuEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.service.UserStateService;
import me.harry0198.infoheads.core.utils.Constants;

import java.util.Optional;
import java.util.UUID;

/**
 * The {@code WizardCmdExecutor} class extends {@link CmdExecutor} to execute a specific
 * command related to the wizard functionality. It puts the player into "placerMode" which
 * is then picked up by block place events.
 */
public class WizardCmdExecutor extends CmdExecutor {

    private final EventDispatcher eventDispatcher;
    private final UserStateService userStateService;
    private final InfoHeadService infoHeadService;
    private final Command command;

    public WizardCmdExecutor(
            Command command,
            EventDispatcher eventDispatcher,
            InfoHeadService infoHeadService,
            UserStateService userStateService,
            LocalizedMessageService localizedMessageService
    ) {
        super(localizedMessageService, Constants.BASE_PERMISSION + "wizard");
        this.command = command;
        this.eventDispatcher = eventDispatcher;
        this.userStateService = userStateService;
        this.infoHeadService = infoHeadService;
    }

    /**
     * Executes the wizard command for the given {@link OnlinePlayer}. If the sender is
     * a player and not already conversing or in placer mode, it sets up the player
     * for placing an InfoHead by sending a message, creating an {@link InfoHeadProperties},
     * and giving the player necessary items. Placer mode is then picked up by block place events.
     *
     * @param sender the {@link OnlinePlayer} of the player executing the command.
     * @return {@code true} if the command was successfully executed, otherwise {@code false}
     */
    @Override
    public boolean executeCmd(OnlinePlayer sender) {
        if (userStateService.getPlacerModeHead(sender).isPresent()) return true;

        // Check for args /if wizard [head id]
        // If head id is provided, fetch head. Otherwise, create new.
        InfoHeadProperties infoHeadProperties;
        if (command.args().length >= 1) {
            String headIdentifier = command.args()[0];
            try {
                Optional<InfoHeadProperties> infoHeadPropertiesOptional = infoHeadService.getInfoHead(UUID.fromString(headIdentifier));
                if (infoHeadPropertiesOptional.isPresent()) {
                    infoHeadProperties = infoHeadPropertiesOptional.get();
                } else {
                    eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(sender, getLocalizedMessageService().getMessage(BundleMessages.NO_HEAD_FOUND)));
                    return false;
                }
            } catch (IllegalArgumentException ex) {
                eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(sender, getLocalizedMessageService().getMessage(BundleMessages.NO_HEAD_FOUND)));
                return false;
            }
        } else {
            infoHeadProperties = new InfoHeadProperties();
        }

        eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(sender, getLocalizedMessageService().getMessage(BundleMessages.INFOHEAD_PLACE)));
        userStateService.addToPlacerMode(sender, infoHeadProperties);

//        HeadStacks.giveHeads(player);
        return true;
    }
}
