package me.harry0198.infoheads.core.event.handlers;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerCommandEvent;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the breakage of an InfoHead.
 */
public class BreakHandler {

    private final static Logger LOGGER = Logger.getLogger(BreakHandler.class.getName());
    private final InfoHeadService infoHeadService;
    private final LocalizedMessageService localizedMessageService;
    private final EventDispatcher eventDispatcher;

    public BreakHandler(InfoHeadService infoHeadService, LocalizedMessageService localizedMessageService, EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
        this.infoHeadService = infoHeadService;
        this.localizedMessageService = localizedMessageService;
    }

    /**
     * Block broken event.
     * @param player
     * @param breakLocation {@link Location} of the breakage.
     */
    public void handle(OnlinePlayer player, Location breakLocation) {
        Optional<InfoHeadProperties> infoHeadPropertiesOptional = infoHeadService.getInfoHead(breakLocation);

        // Ignore if player is not looking at anything / infohead does not exist.
        if (infoHeadPropertiesOptional.isEmpty()) {
            return;
        }

        infoHeadService.removeInfoHead(infoHeadPropertiesOptional.get())
                .exceptionally(e -> {
                    LOGGER.throwing(BreakHandler.class.getName(), "handle", e);
                    return false; // Fatally failed to save.
                }).thenAccept(x -> {
                    LOGGER.log(Level.FINE, "InfoHead break delete stage completed with " + x);
                    if (!x) {
                        eventDispatcher.dispatchEvent(new SendPlayerCommandEvent(player, localizedMessageService.getMessage(BundleMessages.FAILED_TO_REMOVE)));
                    }
                });
        eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(player, localizedMessageService.getMessage(BundleMessages.INFOHEAD_REMOVED)));
    }
}
