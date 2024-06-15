package me.harry0198.infoheads.spigot.ui.edit;

import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.elements.Element;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.spigot.ui.GuiItem;
import me.harry0198.infoheads.spigot.ui.GuiSlot;
import me.harry0198.infoheads.spigot.ui.InventoryGui;
import me.harry0198.infoheads.spigot.ui.builder.ItemBuilder;
import me.harry0198.infoheads.spigot.ui.wizard.WizardGui;
import me.harry0198.infoheads.core.ui.WizardViewModel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EditGui extends InventoryGui {

    private final Map<Integer, Element> idSlots = new HashMap<>();

    public EditGui(InfoHeadProperties configuration) {
        super(5, "Edit InfoHeads");

        // set outside click action
        // TODO

        // If currently holding an item on cursor and gui closes, set it to air.
        setCloseAction(event -> event.getPlayer().setItemOnCursor(new ItemStack(Material.AIR)));

//        // Create and insert the ordered items.
//        for (int slot = 0; slot < configuration.getElements().size(); slot++) {
//            Element<?> each = configuration.getElements().get(slot);
//
//            String title = MessageUtil.getString(MessageUtil.Message.EDIT_ITEM_TITLE);
//            String[] lore = MessageUtil.getStringList(MessageUtil.Message.EDIT_ITEM_LORE);
//            title = title.replaceAll("@type", each.getType().toString());
//
//            List<String> loreNew = new ArrayList<>();
//            for (String l : lore) {
//                l = l.replaceAll("@contents", each.getContent().toString());
//                l = l.replaceAll("@id", String.valueOf(slot));
//                loreNew.add(l);
//            }
//            insert(slot, new GuiItem(
//                    new ItemBuilder(Material.PAPER).glow(true).name(title).lore(loreNew).build(),
//                    event -> {
//                        if (event.getClick().equals(ClickType.RIGHT)) {
//                            event.setCurrentItem(null);
//                        }
//                    }));
//
//            slot++;
//        }
//
//        fillBottom(new GuiItem(
//                new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build(),
//                event -> event.setCancelled(true))
//        );
//
//        insert(new GuiSlot(5, 5), new GuiItem(
//                new ItemBuilder(Material.BARRIER).name(MessageUtil.getString(MessageUtil.Message.BACK)).build(),
//                event -> {
//                    event.setCancelled(true);
//
//                    if (event.getCursor() == null) return;
//                    if (!event.getCursor().getType().equals(Material.AIR)) return;
//                    InfoHeadConfiguration infoHead = InfoHeads.getInstance().getDataStore().getMatchingInfoHead(configuration);
//                    if (infoHead != null) {
//                        infoHead.setElementList(getNewSlots());
//                    }
//                    new WizardGui(new WizardViewModel(InfoHeads.getInstance(), configuration)).open(event.getWhoClicked());
//                }
//        ));
    }

    private LinkedList<Element> getNewSlots() {

        Map<Integer, Element> newSlots = new HashMap<>();

        // Ignores the last slot for complete icon
        for (int i = 0; i < getInventory().getSize() - 9; i++) {

            ItemStack stackAtSlot = fetch(i);

            if (stackAtSlot == null || stackAtSlot.getType().equals(Material.AIR))
                continue;

            int id;
            try {
                String str = stackAtSlot.getItemMeta().getLore().get(0);
                str = ChatColor.stripColor(str);
                id = Integer.parseInt(str); // Will fail if player puts any item into gui
            } catch (NumberFormatException ignore) {
                continue;
            }

            // Gets current slot, puts it in map with element from idSlots
            newSlots.put(i, idSlots.get(id));
        }

        return new LinkedList<>(newSlots.values());
    }
}
