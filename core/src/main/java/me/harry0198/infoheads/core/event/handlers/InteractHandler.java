package me.harry0198.infoheads.core.event.handlers;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.elements.PlayerPermissionElement;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.actions.RemoveTempPlayerPermissionEvent;
import me.harry0198.infoheads.core.event.actions.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.event.inputs.OpenInfoHeadMenuEvent;
import me.harry0198.infoheads.core.model.*;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.Constants;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        if (handAction == HandAction.OFFHAND) return;
        if (!player.hasPermission(Constants.BASE_PERMISSION + "use")) return;

        // if player is sneaking do nothing.
        if (player.isSneaking() && handAction == HandAction.LEFT_CLICK) return;

        Optional<InfoHeadProperties> infoHeadPropertiesOptional = infoHeadService.getInfoHead(interactedWithLocation);
        if (infoHeadPropertiesOptional.isEmpty() || !canUse(player, infoHeadPropertiesOptional.get())) return;

        // When player is sneaking and right clicking, open the wizard.
        if (player.isSneaking() && handAction == HandAction.RIGHT_CLICK) {
            eventDispatcher.dispatchEvent(new OpenInfoHeadMenuEvent(infoHeadPropertiesOptional.get(), player));
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

            // Schedule task later (after delay). This snippet prevents holding a thread while waiting for delay.
            executorService.schedule(() -> {
                if (player.isOnline()) {
                    el.performAction(eventDispatcher, player);
                }
            }, time, TimeUnit.SECONDS);
        }

        executorService.schedule(() -> {
            // Remove temporary permissions.
            elements.stream()
                    .filter(x -> x instanceof PlayerPermissionElement)
                    .forEach(x -> eventDispatcher.dispatchEvent(new RemoveTempPlayerPermissionEvent(player, ((PlayerPermissionElement) x).getPermission())));

        }, time, TimeUnit.SECONDS);

        // Set cool down and mark as executed.
        infoHeadProperties.setUserCoolDown(player);
        infoHeadProperties.setUserExecuted(player);
    }

    private boolean canUse(OnlinePlayer onlinePlayer, InfoHeadProperties infoHeadProperties) {
                // Checks if player has infohead specific perms
        String permission = infoHeadProperties.getPermission();
        if (permission != null)
            if (!onlinePlayer.hasPermission(permission)) {
                eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(onlinePlayer, localizedMessageService.getMessage(BundleMessages.NO_PERMISSION)));
                return false;
            }

        // Checks if player is on cooldown
        if (infoHeadProperties.isOnCoolDown(onlinePlayer)) {
            Long coolDown = infoHeadProperties.getCoolDown(onlinePlayer);
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(onlinePlayer, localizedMessageService.getTimeMessage(coolDown)));
            return false;
        }

        if (infoHeadProperties.isOneTimeUse() && infoHeadProperties.isExecuted(onlinePlayer)) {
            eventDispatcher.dispatchEvent(new SendPlayerMessageEvent(onlinePlayer, localizedMessageService.getMessage(BundleMessages.ONE_TIME)));
            return false;
        }

        return true;
    }
}
