package com.haroldstudios.infoheads.ui.edit;

import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.ui.GuiItem;
import com.haroldstudios.infoheads.ui.GuiSlot;
import com.haroldstudios.infoheads.ui.InventoryGui;
import com.haroldstudios.infoheads.ui.builder.ItemBuilder;
import com.haroldstudios.infoheads.ui.wizard.WizardGui;
import com.haroldstudios.infoheads.ui.wizard.WizardViewModel;
import com.haroldstudios.infoheads.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public final class EditInfoHeadGui extends InventoryGui {

    private final EditInfoHeadViewModel viewModel;

    /**
     * Creates the edit menu for an InfoHead configuration.
     * @param viewModel for this view.
     */
    public EditInfoHeadGui(EditInfoHeadViewModel viewModel, Locale locale) {
        super(6, "Edit InfoHead");
        this.viewModel = viewModel;

        setDefaultClickAction(event -> event.setCancelled(true));

        populate();
    }

    private void populate() {
        fillEmpty(new GuiItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("").build(), null));

        // Close btn
        insert(new GuiSlot(6,5), getCloseGuiItem());

        // Rename btn
        insert(new GuiSlot(1,9), getEditNameGuiItem());

        // Progression path
        populateProgressionSlots(viewModel.getElementsProperty().getValue());
        viewModel.getElementsProperty().addListener(event -> populateProgressionSlots((LinkedList<Element>) event.getNewValue()));
    }

    private GuiItem getCloseGuiItem() {
        return new GuiItem(
                new ItemBuilder(Material.BARRIER).name(MessageUtil.getString(MessageUtil.Message.CLOSE_WIZARD_TITLE)).lore(MessageUtil.getStringList(MessageUtil.Message.CLOSE_WIZARD_LORE)).build(),
                null
        );
    }

    private GuiItem getEditNameGuiItem() {
        return new GuiItem(
                new ItemBuilder(Material.NAME_TAG).name(MessageUtil.getString(MessageUtil.Message.EDIT_NAME_TITLE)).lore(MessageUtil.getStringList(MessageUtil.Message.EDIT_NAME_LORE)).build(),
                event -> viewModel.rename((Player) event.getWhoClicked())
        );
    }

    private void populateProgressionSlots(LinkedList<Element> elements) {
        LinkedList<GuiSlot> progressionSlots = viewModel.getProgressionSlots(9);

        ListIterator<GuiSlot> progressionSlotIterator = progressionSlots.listIterator();
        ListIterator<Element> elementListIterator = elements.listIterator();

        boolean appendPlaced = false; // Has the "append" slot been placed in the menu already. After one has been placed, the rest should be placeholder slots.
        while (progressionSlotIterator.hasNext()) {
            GuiSlot progressionSlot = progressionSlotIterator.next();

            if (!elementListIterator.hasNext() && !appendPlaced) {

                // Slot is clicked, append item.
                insert(progressionSlot, new GuiItem(
                        new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                                .glow(true)
                                .name(Mess)
                                .build(),
                        event -> new WizardGui(new WizardViewModel(InfoHeads.getInstance(), viewModel.getConfiguration())).open(event.getWhoClicked())
                ));

                appendPlaced = true;
                continue;
            }

            // Insert element into progression chain.
            if (elementListIterator.hasNext()) {
                Element element = elementListIterator.next();

                insert(progressionSlot, new GuiItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).glow(true).build(), event -> {}));
                continue;
            }

            // Placeholder slot ("Nothing").
            insert(progressionSlot, new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).glow(true).build(), null));
        }
    }
}
