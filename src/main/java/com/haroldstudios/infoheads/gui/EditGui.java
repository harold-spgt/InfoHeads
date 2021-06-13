package com.haroldstudios.infoheads.gui;

import com.cryptomorin.xseries.XMaterial;
import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.elements.Element;
import com.haroldstudios.infoheads.utils.MessageUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EditGui {

    private final Map<Integer, Element> idSlots = new HashMap<>();
    @Getter private final PaginatedGui gui;
    @Getter private final Player player;

    public EditGui(final InfoHeadConfiguration configuration, final Player player) {
        this.player = player;
        // Sets rows to 5 and max slots to row 4
        gui = new PaginatedGui(5, 4 * 9, "Edit Infoheads");

        getGui().setOutsideClickAction(event -> event.setCancelled(true));
        getGui().setCloseGuiAction(event -> {
            if (event.getPlayer().getItemOnCursor() != null) {
                event.getPlayer().setItemOnCursor(new ItemStack(Material.AIR));
            }
        });

        int slot = 0;
        for (Element each : configuration.getElementList()) {

            Component title = MessageUtil.EDIT_ITEM_TITLE;
            Component[] lore = MessageUtil.EDIT_ITEM_LORE;
            title = title.replaceText(TextReplacementConfig.builder().match("@type").replacement(each.getType().toString()).build());

            List<Component> loreNew = new ArrayList<>();
            for (Component l : lore) {
                l = l.replaceText(TextReplacementConfig.builder().match("@contents").replacement(each.getContent().toString()).build());
                l = l.replaceText(TextReplacementConfig.builder().match("@id").replacement(String.valueOf(slot)).build());
                loreNew.add(l);
            }
            getGui().addItem(new GuiItem(ItemBuilder.from(XMaterial.PAPER.parseMaterial()).glow(true).name(title).lore(loreNew).build(), event -> {
                if (event.getClick().equals(ClickType.RIGHT)) {
                    event.setCurrentItem(null);
                }
            }));

            idSlots.put(slot, each);
            slot++;
        }

        getGui().getFiller().fillBottom(new GuiItem(new ItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()), event -> event.setCancelled(true)));

        getGui().setItem(5, 5, new GuiItem(ItemBuilder.from(XMaterial.BARRIER.parseMaterial()).name(MessageUtil.BACK).build(), event -> {
            event.setCancelled(true);

            if (event.getCursor() == null) return;
            if (!event.getCursor().getType().equals(Material.AIR)) return;
            InfoHeadConfiguration infoHead = InfoHeads.getInstance().getDataStore().getMatchingInfoHead(configuration);
            if (infoHead != null) {
                infoHead.setElementList(getNewSlots());
            }
            new WizardGui(InfoHeads.getInstance(), player, configuration).open();
        }));
    }

    public void open() {
        getGui().open(player);
    }

    public List<Element> getNewSlots() {

        Map<Integer, Element> newSlots = new HashMap<>();

        // Ignores the last slot for complete icon
        // TODO CURRENTLY DOESN'T SUPPORT MULTI PAGES DUE TO GUI LIB WRONG
        //System.out.println(getGui().getGuiItems().keySet().size());
        for (int i = 0; i < getGui().getInventory().getSize() - 9; i++) {

            ItemStack stackAtSlot = getGui().getInventory().getItem(i);
            
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

        return new ArrayList<>(newSlots.values());
    }

}
