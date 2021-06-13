package com.haroldstudios.infoheads.gui;

import com.cryptomorin.xseries.XMaterial;
import com.haroldstudios.infoheads.InfoHeadConfiguration;
import com.haroldstudios.infoheads.InfoHeads;
import com.haroldstudios.infoheads.utils.MessageUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CooldownGui extends AbstractGui {

    public CooldownGui(Player player, InfoHeads plugin, InfoHeadConfiguration infoHeadConfiguration) {
        super(player, plugin, 5, "Cooldown Delay", infoHeadConfiguration);

        getGui().setDefaultClickAction(event -> event.setCancelled(true));

        getGui().getFiller().fill(new GuiItem(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()));

        getGui().setItem(2, 3, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).name(MessageUtil.toComponent("§5Weeks")).glow(true).build()));
        getGui().setItem(2, 4, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).name(MessageUtil.toComponent("§5Days")).glow(true).build()));
        getGui().setItem(2, 5, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).name(MessageUtil.toComponent("§5Hours")).glow(true).build()));
        getGui().setItem(2, 6, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).name(MessageUtil.toComponent("§5Minutes")).glow(true).build()));
        getGui().setItem(2, 7, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).name(MessageUtil.toComponent("§5Seconds")).glow(true).build()));

        // Slots 2 to 8 -> Weeks, Days, Hours, minutes, seconds
        for (int i = 3; i < 8; i++) {
            int finalI = i;
            getGui().setItem(1, finalI, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.LIME_DYE.parseMaterial()))
                    .name(MessageUtil.COOLDOWN_NUM_INC_TITLE)
                    .lore(MessageUtil.COOLDOWN_NUM_INC_LORE).glow(true).build(), event -> {

                // Get item below it
                int slot = getSlotFromRowCol(1, finalI) + 9;

                GuiItem item = getGui().getGuiItem(slot);
                if (item.getItemStack().getType().equals(XMaterial.BARRIER.parseMaterial())) {
                    getGui().updateItem(slot, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.WHITE_WOOL.parseMaterial())).glow(true).setName(item.getItemStack().getItemMeta().getDisplayName()).build()));
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
            getGui().setItem(3, i, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.REDSTONE.parseMaterial()))
                    .name(MessageUtil.COOLDOWN_NUM_DEC_TITLE)
                    .lore(MessageUtil.COOLDOWN_NUM_DEC_LORE).glow(true).build(), event -> {

                // Get item above it
                int slot = getSlotFromRowCol(3, finalI) - 9;


                GuiItem item = getGui().getGuiItem(slot);

                // If item amount is not 1 / it's minimum value
                if (item.getItemStack().getAmount() != 1) {
                    item.getItemStack().setAmount(item.getItemStack().getAmount() - 1);
                    getGui().updateItem(slot, item);
                } else {
                    getGui().updateItem(slot, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).glow(true).setName(item.getItemStack().getItemMeta().getDisplayName()).build()));
                }
            }));
        }

        getGui().setItem(5, 5, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.BARRIER.parseMaterial())).name(MessageUtil.PREV_PAGE).glow(true).build(), event -> new WizardGui(InfoHeads.getInstance(), getPlayer(), getConfiguration()).open()));
        getGui().setItem(5, 9, new GuiItem(ItemBuilder.from(Objects.requireNonNull(XMaterial.EMERALD_BLOCK.parseMaterial())).name(MessageUtil.COMPLETE_ITEM_TITLE).lore(MessageUtil.COMPLETE_ITEM_LORE).glow(true).build(),
                event -> {
                    //Slots 3,4,5,6,7
                    // Does not get modified itself
                    long weeks = getGui().getGuiItem(3 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(3 + 8).getItemStack().getAmount() * 604800000L;
                    long days = getGui().getGuiItem(4 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(4 + 8).getItemStack().getAmount() * 86400000L;
                    long hours = getGui().getGuiItem(5 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(5 + 8).getItemStack().getAmount() * 3600000L;
                    long minutes = getGui().getGuiItem(6 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(6 + 8).getItemStack().getAmount() * 60000L;
                    long seconds = getGui().getGuiItem(7 + 8).getItemStack().getType() == XMaterial.BARRIER.parseMaterial() ? 0 : getGui().getGuiItem(7 + 8).getItemStack().getAmount() * 1000L;

                    // Get cooldown representation in long parallel to Date
                    long time = weeks + days + hours + minutes + seconds;

                    getConfiguration().setCooldown(time);

                    new WizardGui(InfoHeads.getInstance(), getPlayer(), getConfiguration()).open();
                }));
    }
}
