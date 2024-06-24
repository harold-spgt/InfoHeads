package me.harry0198.infoheads.spigot.ui.edit;

import me.harry0198.infoheads.core.config.BundleMessages;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.ui.EditInfoHeadViewModel;
import me.harry0198.infoheads.spigot.ui.GuiItem;
import me.harry0198.infoheads.core.ui.GuiSlot;
import me.harry0198.infoheads.spigot.ui.InventoryGui;
import me.harry0198.infoheads.spigot.ui.builder.ItemBuilder;
import org.bukkit.Material;

import java.util.LinkedList;
import java.util.ListIterator;

public final class EditInfoHeadGui extends InventoryGui {

    private final EditInfoHeadViewModel viewModel;
    private final LocalizedMessageService localizedMessageService;

    /**
     * Creates the edit menu for an InfoHead configuration.
     * @param viewModel for this view.
     */
    public EditInfoHeadGui(EditInfoHeadViewModel viewModel, LocalizedMessageService localizedMessageService) {
        super(6, "Edit InfoHead");
        this.localizedMessageService = localizedMessageService;
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
        viewModel.getElementsProperty().addListener(event -> populateProgressionSlots((LinkedList<Element<?>>) event.getNewValue()));
    }

    private GuiItem getCloseGuiItem() {
        return new GuiItem(
                new ItemBuilder(Material.BARRIER).name(localizedMessageService.getMessage(BundleMessages.CLOSE_WIZARD)).lore(localizedMessageService.getMessageList(BundleMessages.CLOSE_WIZARD_MORE)).build(),
                null
        );
    }

    private GuiItem getEditNameGuiItem() {
        return new GuiItem(
                new ItemBuilder(Material.NAME_TAG).name(localizedMessageService.getMessage(BundleMessages.EDIT_NAME)).lore(localizedMessageService.getMessageList(BundleMessages.EDIT_NAME_MORE)).build(),
                event -> {} //TODO rename viewmodel.
        );
    }

    private void populateProgressionSlots(LinkedList<Element<?>> elements) {
        LinkedList<GuiSlot> progressionSlots = viewModel.getProgressionSlots(9);

        ListIterator<GuiSlot> progressionSlotIterator = progressionSlots.listIterator();
        ListIterator<Element<?>> elementListIterator = elements.listIterator();

        boolean appendPlaced = false; // Has the "append" slot been placed in the menu already. After one has been placed, the rest should be placeholder slots.
        while (progressionSlotIterator.hasNext()) {
            GuiSlot progressionSlot = progressionSlotIterator.next();

            if (!elementListIterator.hasNext() && !appendPlaced) {

                // Slot is clicked, append item.
                insert(progressionSlot, new GuiItem(
                        new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE)
                                .glow(true)
                                .name("todo") //TODO
                                .build(),
                        event -> {}//TODO append item viewmodel.
                ));

                appendPlaced = true;
                continue;
            }

            // Insert element into progression chain.
            if (elementListIterator.hasNext()) {
                Element<?> element = elementListIterator.next();
                // make use of toString

                insert(progressionSlot, new GuiItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).glow(true).build(), event -> {}));
                continue;
            }

            // Placeholder slot ("Nothing").
            insert(progressionSlot, new GuiItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).glow(true).build(), null));
        }
    }
}
