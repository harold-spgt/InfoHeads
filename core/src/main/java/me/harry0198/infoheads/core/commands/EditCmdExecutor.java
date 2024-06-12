package me.harry0198.infoheads.core.commands;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
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

    /**
     * Class constructor.
     * @param infoHeadService the {@link InfoHeadService} instance used to manage InfoHeads data.
     */
    public EditCmdExecutor(LocalizedMessageService localizedMessageService, InfoHeadService infoHeadService) {
        super(localizedMessageService, Constants.ADMIN_PERMISSION);
        this.infoHeadService = infoHeadService;
    }

    /**
     * Executes the edit command for the given {@link Player}. If the sender is
     * a player, it checks if the targeted block contains an InfoHead. If it does,
     * it proceeds to handle the editing logic.
     *
     * @param player the {@link OnlinePlayer} executing the command.
     * @return {@code true} if the command was successfully executed, otherwise {@code false}
     */
    @Override
    public boolean executeCmd(OnlinePlayer player) {
        Optional<InfoHeadProperties> infoHeadPropertiesOptional = infoHeadService.getInfoHead(player.getLookingAt());
        if (infoHeadPropertiesOptional.isEmpty()) {

            player.sendMessage(getLocalizedMessageService().getMessage(BundleMessages.NO_INFOHEAD_AT_LOCATION));
            return true;
        }

//        new EditInfoHeadGui(new EditInfoHeadViewModel(headAtLoc)).open(player);
        return true;
    }
}
