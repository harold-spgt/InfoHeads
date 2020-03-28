package me.harry0198.infoheads.inventory;

import me.harry0198.infoheads.InfoHeads;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public final class Inventory {

    private static HashMap<UUID, ItemStack[]> items = new HashMap<>();
    private static HashMap<UUID, ItemStack[]> armour = new HashMap<>();

    /**
     * Store the inventory
     *
     * @param player Player Entity
     */
    private static void storeAndClearInventory(Player player){
        UUID uuid = player.getUniqueId();

        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armourContents = player.getInventory().getArmorContents();

        items.put(uuid, contents);
        armour.put(uuid, armourContents);

        player.getInventory().clear();

        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    /**
     * Restore original inventory before wizard setup.
     *
     * @param player Player entity
     */
    public static void restoreInventory(Player player){
        UUID uuid = player.getUniqueId();

        ItemStack[] contents = items.get(uuid);
        ItemStack[] armorContents = armour.get(uuid);

        if(contents != null) player.getInventory().setContents(contents);

        else player.getInventory().clear();

        if(armorContents != null)
            player.getInventory().setArmorContents(armorContents);

        else {
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
        }
    }

    public static void giveHeads(InfoHeads plugin, Player player) {
        Inventory.storeAndClearInventory(player);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> new HeadStacks().giveItems(player));
    }
}
