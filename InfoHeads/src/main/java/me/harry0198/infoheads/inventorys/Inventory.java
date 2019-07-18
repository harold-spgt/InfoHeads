package me.harry0198.infoheads.inventorys;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.harry0198.infoheads.InfoHeads;

public class Inventory {
	
	private InfoHeads infoHeads;

	public Inventory(InfoHeads infoHeads) {
		this.infoHeads = infoHeads;
	}
	
	/**
	 * Store the inventory
	 * 
	 * @param player Player Entity
	 */
	public void storeAndClearInventory(Player player){
	    UUID uuid = player.getUniqueId();

	    ItemStack[] contents = player.getInventory().getContents();
	    ItemStack[] armorContents = player.getInventory().getArmorContents();

	    infoHeads.items.put(uuid, contents);
	    infoHeads.armor.put(uuid, armorContents);

	    player.getInventory().clear();

	    player.getInventory().setHelmet(null);
	    player.getInventory().setChestplate(null);
	    player.getInventory().setLeggings(null);
	    player.getInventory().setBoots(null);
	}

	/**
	 * Restore original inventory
	 * 
	 * @param player Player entity
	 */
	public void restoreInventory(Player player){
	    UUID uuid = player.getUniqueId();

	    ItemStack[] contents = infoHeads.items.get(uuid);
	    ItemStack[] armorContents = infoHeads.armor.get(uuid);

	    if(contents != null){
	        player.getInventory().setContents(contents);
	    }
	    else{ // if inventorycontents is empty, clear inv
	        player.getInventory().clear();
	    }

	    if(armorContents != null){
	        player.getInventory().setArmorContents(armorContents);
	    }
	    else{ //If the player was not wearing armour, clear it
	        player.getInventory().setHelmet(null);
	        player.getInventory().setChestplate(null);
	        player.getInventory().setLeggings(null);
	        player.getInventory().setBoots(null);
	    }
	}
	
	public void infoHeadsInventory(Player player) {

		infoHeads.headStacks.giveItems(player);
		
	}


}
