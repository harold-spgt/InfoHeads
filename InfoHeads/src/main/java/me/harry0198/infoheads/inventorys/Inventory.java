package me.harry0198.infoheads.inventorys;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.harry0198.infoheads.InfoHeads;

public class Inventory {
	
	/**
	 * Store the inventory
	 * 
	 * @param player Player Entity
	 */
	public void storeAndClearInventory(Player player){
	    UUID uuid = player.getUniqueId();

	    ItemStack[] contents = player.getInventory().getContents();
	    ItemStack[] armorContents = player.getInventory().getArmorContents();

	    getInstance().items.put(uuid, contents);
	    getInstance().armor.put(uuid, armorContents);

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
	public void restoreInventory(Player player){
	    UUID uuid = player.getUniqueId();

	    ItemStack[] contents = getInstance().items.get(uuid);
	    ItemStack[] armorContents = getInstance().armor.get(uuid);

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
	
	public void infoHeadsInventory(Player player) {
		getInstance().headStacks.giveItems(player);
	}

	private InfoHeads getInstance() {
		return InfoHeads.getInstance();
	}
}
