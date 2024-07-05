package me.harry0198.infoheads.core.ui;


import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.actions.SendPlayerCommandEvent;
import me.harry0198.infoheads.core.event.inputs.GetCoolDownInputEvent;
import me.harry0198.infoheads.core.event.inputs.GetNameInputEvent;
import me.harry0198.infoheads.core.event.inputs.GetPermissionInputEvent;
import me.harry0198.infoheads.core.event.inputs.OpenAddActionMenuEvent;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.utils.SimpleProperty;

import java.util.LinkedList;

public class EditInfoHeadViewModel extends ViewModel {

    private final InfoHeadProperties configuration;
    private final SimpleProperty<Boolean> isOneTimeUseProperty;
    private final SimpleProperty<LinkedList<Element<?>>> elementsProperty;

    public EditInfoHeadViewModel(EventDispatcher eventDispatcher, InfoHeadProperties infoHeadConfiguration) {
        super(eventDispatcher);
        this.configuration = infoHeadConfiguration;
        this.elementsProperty = new SimpleProperty<>(infoHeadConfiguration.getElements());
        this.isOneTimeUseProperty = new SimpleProperty<>(infoHeadConfiguration.isOneTimeUse());
    }

    /**
     * Gets the elements that the InfoHead to edit consists of.
     * @return {@link LinkedList} of the elements the InfoHead consists of.
     */
    public SimpleProperty<LinkedList<Element<?>>> getElementsProperty() {
        return elementsProperty;
    }

    public void beginNewElementFlow(OnlinePlayer onlinePlayer) {
        getEventDispatcher().dispatchEvent(new OpenAddActionMenuEvent(this.configuration, onlinePlayer));
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

    /**
     * Gets the progression snake for the gui. Consists of a constant zigzag shape. (lrl)
     * @return A {@link LinkedList} with the progression snake for the gui.
     */
    public LinkedList<GuiSlot> getProgressionSlots(int maxCols) {
        LinkedList<GuiSlot> progressionSlots = new LinkedList<>();

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
