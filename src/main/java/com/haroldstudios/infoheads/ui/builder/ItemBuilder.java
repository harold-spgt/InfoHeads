package com.haroldstudios.infoheads.ui.builder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * {@link ItemStack} utility to allow quick and easy item building in a
 * chain.
 */
public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        if (material == null) throw new IllegalArgumentException("Material cannot be null");
        this.itemStack = new ItemStack(material);

        // Get ItemMeta or assign it one.
        this.itemMeta = itemStack.hasItemMeta() ?
                itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    /**
     * Sets the display name of the item.
     *
     * @param name The String name to give.
     * @return {@link ItemBuilder}
     */
    public ItemBuilder name(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the amount of items
     *
     * @param amount the amount of items
     * @return {@link ItemBuilder}
     */
    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore Lore lines as args
     * @return {@link ItemBuilder}
     */
    public ItemBuilder lore(String... lore) {
        return lore(Arrays.stream(lore).toList());
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore {@link List} with the lore lines
     * @return {@link ItemBuilder}
     */
    public ItemBuilder lore(List<String> lore) {
        itemMeta.setLore(lore);
        return this;
    }

    /**
     * Adds or removes the {@link ItemStack} glow
     * @param glow Should the item glow.
     * @return {@link ItemBuilder}
     */
    public ItemBuilder glow(boolean glow) {
        if (glow) {
            // Add enchant and hide it.
            itemMeta.addEnchant(Enchantment.LURE, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            // If false, remove all enchantments to remove glow.
            for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                itemMeta.removeEnchant(enchantment);
            }
        }

        return this;
    }

    /**
     * Build the {@link ItemStack} from the provided builder options.
     * @return {@link ItemStack} with options applied.
     */
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
