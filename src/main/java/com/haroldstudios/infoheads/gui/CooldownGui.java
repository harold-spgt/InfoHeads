package com.haroldstudios.infoheads.gui;

import com.cryptomorin.xseries.XMaterial;
import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.utils.MessageUtil;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CooldownGui extends AbstractGui {

    public CooldownGui(Player player, InfoHeads plugin, InfoHeadConfiguration infoHeadConfiguration) {
        super(player, plugin, 5, "Cooldown Delay", infoHeadConfiguration);

        getGui().setDefaultClickAction(event -> event.setCancelled(true));

        getGui().getFiller().fill(new GuiItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()));

        getGui().setItem(2, 3, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).setName("§5Weeks").glow(true).build()));
        getGui().setItem(2, 4, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).setName("§5Days").glow(true).build()));
        getGui().setItem(2, 5, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).setName("§5Hours").glow(true).build()));
        getGui().setItem(2, 6, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).setName("§5Minutes").glow(true).build()));
        getGui().setItem(2, 7, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).setName("§5Seconds").glow(true).build()));

        // Slots 2 to 8 -> Weeks, Days, Hours, minutes, seconds
        for (int i = 3; i < 8; i++) {
            int finalI = i;
            getGui().setItem(1, finalI, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.LIME_DYE.parseMaterial()))
                    .setName(MessageUtil.COOLDOWN_NUM_INC_TITLE)
                    .setLore(MessageUtil.COOLDOWN_NUM_INC_LORE).glow(true).build(), event -> {

                // Get item below it
                int slot = getSlotFromRowCol(1, finalI) + 9;

                GuiItem item = getGui().getGuiItem(slot);
                if (item.getItemStack().getType().equals(XMaterial.BARRIER.parseMaterial())) {
                    getGui().updateItem(slot, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.WHITE_WOOL.parseMaterial())).glow(true).setName(item.getItemStack().getItemMeta().getDisplayName()).build()));
                    return;
                }
                // If item amount is not 64 / it's maximum value
                if (item.getItemStack().getAmount() != 64) {
                    item.getItemStack().setAmount(item.getItemStack().getAmount() + 1);
                    getGui().updateItem(slot, item);
                }
            }));
        }

        for (int i = 3; i < 8; i++) {
            int finalI = i;
            getGui().setItem(3, i, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.REDSTONE.parseMaterial()))
                    .setName(MessageUtil.COOLDOWN_NUM_DEC_TITLE)
                    .setLore(MessageUtil.COOLDOWN_NUM_DEC_LORE).glow(true).build(), event -> {

                // Get item above it
                int slot = getSlotFromRowCol(3, finalI) - 9;


                GuiItem item = getGui().getGuiItem(slot);

                // If item amount is not 1 / it's minimum value
                if (item.getItemStack().getAmount() != 1) {
                    item.getItemStack().setAmount(item.getItemStack().getAmount() - 1);
                    getGui().updateItem(slot, item);
                } else {
                    getGui().updateItem(slot, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).glow(true).setName(item.getItemStack().getItemMeta().getDisplayName()).build()));
                }
            }));
        }

        getGui().setItem(5, 5, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).setName(MessageUtil.PREV_PAGE).glow(true).build(), event -> new WizardGui(InfoHeads.getInstance(), getPlayer(), getConfiguration()).open()));
        getGui().setItem(5, 9, new GuiItem(new ItemBuilder(Objects.requireNonNull(XMaterial.EMERALD_BLOCK.parseMaterial())).setName(MessageUtil.COMPLETE_ITEM_TITLE).setLore(MessageUtil.COMPLETE_ITEM_LORE).glow(true).build(),
                event -> {
                    //Slots 3,4,5,6,7
                    // Does not get modified itself
                    long weeks = getGui().getGuiItem(3 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(3 + 8).getItemStack().getAmount() * 604800000;
                    long days = getGui().getGuiItem(4 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(4 + 8).getItemStack().getAmount() * 86400000;
                    long hours = getGui().getGuiItem(5 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(5 + 8).getItemStack().getAmount() * 3600000;
                    long minutes = getGui().getGuiItem(6 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(6 + 8).getItemStack().getAmount() * 60000;
                    long seconds = getGui().getGuiItem(7 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(7 + 8).getItemStack().getAmount() * 1000;

                    // Get cooldown representation in long parallel to Date
                    long time = weeks + days + hours + minutes + seconds;

                    getConfiguration().setCooldown(time);

                    new WizardGui(InfoHeads.getInstance(), getPlayer(), getConfiguration()).open();
                }));
    }
}
