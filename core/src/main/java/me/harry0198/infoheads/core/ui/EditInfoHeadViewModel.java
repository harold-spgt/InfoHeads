package me.harry0198.infoheads.core.ui;


import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.SendPlayerCommandEvent;
import me.harry0198.infoheads.core.event.types.SendPlayerMessageEvent;
import me.harry0198.infoheads.core.event.types.GetCoolDownInputEvent;
import me.harry0198.infoheads.core.event.types.GetNameInputEvent;
import me.harry0198.infoheads.core.event.types.GetPermissionInputEvent;
import me.harry0198.infoheads.core.event.types.OpenAddActionMenuEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.SimpleProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class EditInfoHeadViewModel extends ViewModel {

    private static final Logger LOGGER = Logger.getLogger(EditInfoHeadViewModel.class.getName());

    private final InfoHeadProperties configuration;
    private final InfoHeadService infoHeadService;
    private final LocalizedMessageService localizedMessageService;
    private final SimpleProperty<Boolean> isOneTimeUseProperty;
    private final SimpleProperty<List<Element<?>>> elementsProperty;

    public EditInfoHeadViewModel(EventDispatcher eventDispatcher, InfoHeadService infoHeadService, InfoHeadProperties infoHeadConfiguration, LocalizedMessageService localizedMessageService) {
        super(eventDispatcher);
        this.configuration = infoHeadConfiguration;
        this.infoHeadService = infoHeadService;
        this.localizedMessageService = localizedMessageService;
        this.elementsProperty = new SimpleProperty<>(infoHeadConfiguration.getElements());
        this.isOneTimeUseProperty = new SimpleProperty<>(infoHeadConfiguration.isOneTimeUse());

        this.elementsProperty.addListener(change -> configuration.setElements((List<Element<?>>) change.getNewValue()));
        this.isOneTimeUseProperty.addListener(change -> configuration.setOneTimeUse((boolean) change.getNewValue()));
    }

    /**
     * Gets the elements that the InfoHead to edit consists of.
     * @return {@link LinkedList} of the elements the InfoHead consists of.
     */
    @SuppressWarnings("squid:S1452")
    public SimpleProperty<List<Element<?>>> getElementsProperty() {
        return elementsProperty;
    }

    public void beginNewElementFlow(OnlinePlayer onlinePlayer) {
        getEventDispatcher().dispatchEvent(new OpenAddActionMenuEvent(this.configuration, onlinePlayer));
    }

    public void save(OnlinePlayer onlinePlayer) {
        infoHeadService.saveInfoHeadToRepository(configuration).exceptionally(ex -> false).thenAccept(success -> {
            if (Boolean.FALSE.equals(success)) {
                getEventDispatcher().dispatchEvent(new SendPlayerMessageEvent(onlinePlayer, localizedMessageService.getMessage(BundleMessages.SAVE_FAILED)));
            }
        });
    }

    /**
     * Initiates conversation for requesting input for the new infohead name.
     * @param player Who has requested the rename?
     */
    public void rename(OnlinePlayer player) {
        requestClose();
        getEventDispatcher().dispatchEvent(new GetNameInputEvent(configuration, player));
    }

    public void permissionToUse(OnlinePlayer player) {
        requestClose();
        getEventDispatcher().dispatchEvent(new GetPermissionInputEvent(configuration, player));
    }


    public void setOneTimeUse(boolean oneTimeUse) {
        configuration.setOneTimeUse(!oneTimeUse);
        isOneTimeUseProperty.setValue(oneTimeUse);
    }

    public SimpleProperty<Boolean> getIsOneTimeUseProperty() {
        return isOneTimeUseProperty;
    }

    public void setLocation(OnlinePlayer player) {
        getShouldCloseProperty().setValue(true);
        getEventDispatcher().dispatchEvent(new SendPlayerCommandEvent(player, "infoheads wizard " + configuration.getId()));
    }

    public void getCoolDownInput(OnlinePlayer onlinePlayer) {
        getEventDispatcher().dispatchEvent(new GetCoolDownInputEvent(configuration, onlinePlayer));
    }

    public void deleteElement(Element<?> element) {
        List<Element<?>> elements = elementsProperty.getValue();
        List<Element<?>> newElements = new LinkedList<>(elements);
        newElements.remove(element);
        elementsProperty.setValue(newElements);
    }

    public void shiftOrderLeft(Element<?> element) {
        LOGGER.fine("Shifting Order for element " + element.getType() + " left.");
        List<Element<?>> elements = elementsProperty.getValue();
        LinkedList<Element<?>> newElements = new LinkedList<>(elements);

        int currentIndex = newElements.indexOf(element);

        // if element does not exist or is first in sequence, do nothing.
        if (currentIndex == -1 || currentIndex == 0) return;

        newElements.remove(element);
        newElements.add(currentIndex - 1, element);
        elementsProperty.setValue(newElements);
    }

    public void shiftOrderRight(Element<?> element) {
        List<Element<?>> elements = elementsProperty.getValue();
        LinkedList<Element<?>> newElements = new LinkedList<>(elements);

        int currentIndex = newElements.indexOf(element);

        // Ensure element exists in list and is not already at the end of the list.
        if (currentIndex != -1 && currentIndex < newElements.size() - 1) {
            newElements.remove(currentIndex);
            newElements.add(currentIndex+1, element);
            elementsProperty.setValue(newElements);
        }
    }

    /**
     * Gets the progression snake for the gui. Consists of a constant zigzag shape. (lrl)
     * @return A {@link LinkedList} with the progression snake for the gui.
     */
    @SuppressWarnings("java:S135")
    public List<GuiSlot> getProgressionSlots(int maxCols) {
        var progressionSlots = new LinkedList<GuiSlot>();

        if (maxCols < 1) return progressionSlots;  // Handle edge cases

        int currentCol = 1;
        while (currentCol <= maxCols) {
            // Add the down part of the zigzag (2 to 5)
            for (int row = 2; row <= 5; row++) {
                progressionSlots.add(new GuiSlot(row, currentCol));
            }
            if (++currentCol > maxCols) break;

            progressionSlots.add(new GuiSlot(5, currentCol));

            if (++currentCol > maxCols) break;

            // Add the up part of the zigzag (5 to 2)
            for (int row = 5; row >= 2; row--) {
                progressionSlots.add(new GuiSlot(row, currentCol));
            }

            if (++currentCol > maxCols) break;

            progressionSlots.add(new GuiSlot(2, currentCol));

            currentCol++;
        }

        return progressionSlots;
    }
}
