package me.harry0198.infoheads.core.event.handlers;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.actions.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.model.*;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.service.InfoHeadService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InteractHandler {

    private final InfoHeadService infoHeadService;
    private final LocalizedMessageService localizedMessageService;
    private final EventDispatcher eventDispatcher;

    public InteractHandler(InfoHeadService infoHeadService, LocalizedMessageService localizedMessageService, EventDispatcher eventDispatcher) {
        this.infoHeadService = infoHeadService;
        this.localizedMessageService = localizedMessageService;
        this.eventDispatcher = eventDispatcher;
    }

    public void interactWithHead(OnlinePlayer player, Location interactedWithLocation, HandAction handAction) {
        // if player is sneaking do nothing.
        //TODO check permission for "USE".
        if (player.isSneaking() && handAction == HandAction.LEFT_CLICK) return;

        Optional<InfoHeadProperties> infoHeadPropertiesOptional = infoHeadService.getInfoHead(interactedWithLocation);
        if (infoHeadPropertiesOptional.isEmpty() || !canUse(player, infoHeadPropertiesOptional.get())) return;

        if (player.isSneaking() && handAction == HandAction.RIGHT_CLICK) {
            // OPEN WIZARD.
            return;
        }

        InfoHeadProperties infoHeadProperties = infoHeadPropertiesOptional.get();

        final List<Element<?>> elements = infoHeadProperties.getElements();

        // Loops through elements
        Iterator<Element<?>> element = elements.iterator();
        long time = 0;
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        while (element.hasNext()) {
            Element<?> el = element.next();
            if (el.getType().equals(Element.InfoHeadType.DELAY))
                time = time + ((TimePeriod) el.getContent()).toSeconds();

            // Schedule task later (after delay). This snippet prevents consuming a thread while waiting for delay.
            executorService.schedule(() -> {
                if (player.isOnline()) {
                    el.performAction(eventDispatcher, player);
                }
            }, time, TimeUnit.SECONDS);
        }

        executorService.schedule(() -> {
            // TODO remove permission, add user's name to cool down, check if configuration is execute once.
        }, time, TimeUnit.SECONDS);
    }

    private boolean canUse(OnlinePlayer onlinePlayer, InfoHeadProperties infoHeadProperties) {
                // Checks if player has infohead specific perms
        String permission = infoHeadProperties.getPermission();
        if (permission != null)
            if (!onlinePlayer.hasPermission(permission)) {
                eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(onlinePlayer, localizedMessageService.getMessage(BundleMessages.NO_PERMISSION)));
                return false;
            }

//        // Checks if player is on cooldown
//        if (infoHeadService.getTimestamps().containsKey(e.getPlayer().getUniqueId())) {
//
//            long playerTimestamp = configuration.getTimestamps().get(e.getPlayer().getUniqueId());
//            if (playerTimestamp > System.currentTimeMillis()) {
//                e.getPlayer().sendMessage(MessageUtil.returnTimeMessage(playerTimestamp - System.currentTimeMillis(), MessageUtil.getString(MessageUtil.Message.COOLDOWN)));
//                return;
//            } else {
//                configuration.getTimestamps().remove(e.getPlayer().getUniqueId());
//            }
//        }
//
//        if (configuration.getExecuted().contains(e.getPlayer().getUniqueId())) {
//            MessageUtil.sendMessage(e.getPlayer(), MessageUtil.Message.ONE_TIME);
//            return;
//        }
        return true;
    }

//    /**
//     * Define if the hand used in event is off hand
//     *
//     * @param event Event to analyse
//     * @return Is off hand
//     */
//    private boolean isOffHand(final PlayerInteractEvent event) {
//        try {
//            return event.getHand() == EquipmentSlot.OFF_HAND;
//        } catch (NoSuchMethodError e) {
//            return false;
//        }
//    }
}
