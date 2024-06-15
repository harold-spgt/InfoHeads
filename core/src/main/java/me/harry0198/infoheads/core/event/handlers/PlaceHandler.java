package me.harry0198.infoheads.core.event.handlers;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.model.Location;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.service.InfoHeadService;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles what should happen when a user places an InfoHead at a given location.
 */
public class PlaceHandler {

    private static final Logger LOGGER = Logger.getLogger(PlaceHandler.class.getName());
    private final InfoHeadService infoHeadService;
    private final LocalizedMessageService localizedMessageService;

    /**
     * Class constructor.
     * @param infoHeadService {@link InfoHeadService} instance.
     * @param localizedMessageService {@link LocalizedMessageService} to provide localized messages to user.
     */
    public PlaceHandler(InfoHeadService infoHeadService, LocalizedMessageService localizedMessageService) {
        this.infoHeadService = infoHeadService;
        this.localizedMessageService = localizedMessageService;
    }

    /**
     * Handles when a player places an infohead.
     * A new infohead will be created.
     * @param player {@link OnlinePlayer} Who placed the infohead.
     * @param location {@link Location} Infohead was placed at.
     */
    public void placeHead(OnlinePlayer player, Location location) {
       placeHead(player, location, null);
    }

    /**
     * Handles when a player places an infohead. If provided uuid is not null,
     * will attempt to retrieve data from repository and relocate and enable the infohead.
     * Otherwise, a new infohead will be created
     * @param player {@link OnlinePlayer} Who placed the infohead.
     * @param location {@link Location} Infohead was placed at.
     * @param infoheadUUID {@link UUID} of the infohead placed (if one exists). May be null.
     */
    public void placeHead(OnlinePlayer player, Location location, UUID infoheadUUID) {
        Optional<InfoHeadProperties> existingInfoHeadOptional = infoheadUUID == null ?
                Optional.empty() : infoHeadService.getInfoHead(infoheadUUID);

        InfoHeadProperties infoHeadProperties;
        if (existingInfoHeadOptional.isEmpty()) {
            infoHeadProperties = new InfoHeadProperties(
                    UUID.randomUUID(),
                    "{" + location.x() + ", " + location.y() + ", " + location.z() + ": " + location.dimension() + "}",
                    location,
                    null,
                    null,
                    false,
                    true
            );
        } else {
            infoHeadProperties = existingInfoHeadOptional.get();
            infoHeadProperties.setLocation(location);
            infoHeadProperties.setEnabled(true);
        }

        infoHeadService.addInfoHead(infoHeadProperties)
                .exceptionally(e -> {
                    LOGGER.throwing(PlaceHandler.class.getName(), "placeHead", e);
                    return false; // Fatally failed to save.
                }).thenAccept(x -> {
                    LOGGER.log(Level.FINE, "InfoHead placement add stage completed with " + x);
                    if (x) {
                        player.sendMessage(localizedMessageService.getMessage(BundleMessages.INFOHEAD_ADDED));
                        //TODO
//                        new WizardGui(new WizardViewModel(plugin, configuration)).open(e.getPlayer());
                    } else {
                        player.sendMessage(localizedMessageService.getMessage(BundleMessages.FAILED_TO_ADD));
                    }
                });
    }
}
