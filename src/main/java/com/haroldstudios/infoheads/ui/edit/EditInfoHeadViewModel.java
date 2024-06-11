package com.haroldstudios.infoheads.ui.edit;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.ui.BaseGuiViewModel;
import com.haroldstudios.infoheads.ui.GuiSlot;
import com.haroldstudios.infoheads.ui.SimpleProperty;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class EditInfoHeadViewModel extends BaseGuiViewModel {

    private final InfoHeadConfiguration configuration;
    private final SimpleProperty<LinkedList<Element>> elementsProperty;

    public EditInfoHeadViewModel(InfoHeadConfiguration infoHeadConfiguration) {
        this.configuration = infoHeadConfiguration;
        this.elementsProperty = new SimpleProperty<>(infoHeadConfiguration.getElementList());
    }

    /**
     * Gets the elements that the InfoHead to edit consists of.
     * @return {@link LinkedList} of the elements the InfoHead consists of.
     */
    public SimpleProperty<LinkedList<Element>> getElementsProperty() {
        return elementsProperty;
    }

    public InfoHeadConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Initiates conversation for requesting input for the new infohead name.
     * @param entity Who has requested the rename.
     */
    public void rename(Player entity) {
        requestClose();
        InfoHeads.getInputFactory(configuration, Element.InfoHeadType.RENAME).buildConversation(entity).begin();
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
