package me.harry0198.infoheads.core.eventhandler;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.Player;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.Constants;

import java.util.Optional;

/**
 * Handles the breakage of an InfoHead.
 */
public class BreakHandler {

    private final InfoHeadService infoHeadService;
    private final LocalizedMessageService localizedMessageService;

    public BreakHandler(InfoHeadService infoHeadService, LocalizedMessageService localizedMessageService) {
        this.infoHeadService = infoHeadService;
        this.localizedMessageService = localizedMessageService;
    }

    /**
     * Block broken event.
     * @param player
     * @param breakLocation {@link Location} of the breakage.
     */
    public void handle(OnlinePlayer player, Location breakLocation) {
        if (!player.hasPermission(Constants.ADMIN_PERMISSION)) return;
        if (!player.isSneaking()) return;

        Optional<InfoHeadProperties> infoHeadPropertiesOptional = infoHeadService.getInfoHead(breakLocation);

        // Ignore if player is not looking at anything.
        if (infoHeadPropertiesOptional.isEmpty()) {
            return;
        }

        infoHeadService.removeInfoHead(infoHeadPropertiesOptional.get());
        player.sendMessage(localizedMessageService.getMessage(BundleMessages.INFOHEAD_REMOVED));
    }
}
