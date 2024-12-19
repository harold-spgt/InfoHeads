package me.harry0198.infoheads.core.event.handlers;

import com.google.inject.Inject;
import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.service.MessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.event.types.OpenMenuMenuEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.service.UserStateService;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles what should happen when a user places an InfoHead at a given location.
 */
public class PlaceHandler {

    private static final Logger LOGGER = Logger.getLogger(PlaceHandler.class.getName());
    private final InfoHeadService infoHeadService;
    private final MessageService messageService;
    private final EventDispatcher eventDispatcher;
    private final UserStateService userStateService;

    /**
     * Class constructor.
     * @param infoHeadService {@link InfoHeadService} instance.
     * @param messageService {@link MessageService} to provide localized messages to user.
     */
    @Inject
    public PlaceHandler(InfoHeadService infoHeadService, UserStateService userStateService, MessageService messageService, EventDispatcher eventDispatcher) {
        this.infoHeadService = infoHeadService;
        this.messageService = messageService;
        this.eventDispatcher = eventDispatcher;
        this.userStateService = userStateService;
    }


    /**
     * Handles when a player places an infohead. If provided uuid is not null,
     * will attempt to retrieve data from repository and relocate and enable the infohead.
     * Otherwise, a new infohead will be created
     * @param player {@link OnlinePlayer} Who placed the infohead.
     * @param location {@link Location} Infohead was placed at.
     */
    public void placeHead(OnlinePlayer player, Location location) {

        Optional<InfoHeadProperties> infoHeadPropertiesOptional = userStateService.getPlacerModeHead(player);

        // User must be in placer mode.
        if (infoHeadPropertiesOptional.isEmpty()) return;

        // Remove from placer mode
        userStateService.removeFromPlacerMode(player);

        InfoHeadProperties infoHeadProperties = infoHeadPropertiesOptional.get();
        infoHeadService.clearLocation(infoHeadProperties.getLocation());
        infoHeadProperties.setLocation(location);

        infoHeadService.addInfoHead(infoHeadProperties)
                .exceptionally(e -> {
                    LOGGER.throwing(PlaceHandler.class.getName(), "placeHead", e);
                    return false; // Fatally failed to save.
                }).thenAccept(x -> {
                    LOGGER.log(Level.FINE, "InfoHead placement add stage completed with {0}", x);
                    if (Boolean.TRUE.equals(x)) {
                        eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(player, messageService.getMessage(BundleMessages.INFOHEAD_ADDED)));
                        eventDispatcher.dispatchEvent(new OpenMenuMenuEvent(infoHeadProperties, player));
                    } else {
                        eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(player, messageService.getMessage(BundleMessages.FAILED_TO_ADD)));
                    }
                });
    }
}
