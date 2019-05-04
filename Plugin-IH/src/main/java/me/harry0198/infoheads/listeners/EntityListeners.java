package me.harry0198.infoheads.listeners;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.inventorys.Inventory;
import me.harry0198.infoheads.utils.Constants;
import me.harry0198.infoheads.utils.Utils;
import org.bukkit.inventory.EquipmentSlot;

public class EntityListeners implements Listener {
	
	protected InfoHeads b;

	public EntityListeners(InfoHeads b) {
		this.b = b;

	}
	
	/**
	 * Triggered Every time a block is placed
	 * Triggered pass after player hits 'PlaceBlockPrompt' and
	 * they're added to the map for naming
	 * 
	 * @param e
	 */
	private List<String> names = new ArrayList<>();
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!(b.namedComplete.contains(e.getPlayer()))) {return;}
		Utils.sendMessage(e.getPlayer(), "&aInfoHeads Wizard: &fReceived Placement!");
		Utils.sendMessage(e.getPlayer(), "&aInfoHeads Wizard: &fCreation complete!");
		
		String name = b.name.get(e.getPlayer());
		
		b.getConfig().set(name + ".x", e.getBlockPlaced().getX());
		b.getConfig().set(name + ".y", e.getBlockPlaced().getY());
		b.getConfig().set(name + ".z", e.getBlockPlaced().getZ());
		addToList(name);
		b.saveConfig();
		
		
		new Inventory(b).restoreInventory(e.getPlayer());
		b.namedComplete.remove(e.getPlayer());
		b.name.remove(e.getPlayer());
		
		// Update the holdings list
		b.infoHeadsData();
	}
	
	/**
	 * Triggered Every time a player interacts with something
	 * 
	 * @param e
	 */
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent e) {
		// Check for one click registered via reflection
        if (b.versionHandler.triggerOnce(e) == false) { return;}
		//if (e.getHand() == EquipmentSlot.OFF_HAND) { return;} // So only one click is registered
		if (InfoHeads.getPermissions().playerHas(e.getPlayer(), Constants.BASE_PERM + "use") == false) { return;} // perms check
		// Check if they've clicked a valid block
		try {
			b.x.containsValue(e.getClickedBlock().getX());
		} catch (NullPointerException npe) { return; }
		// Check if we have the block
		if (!(b.x.containsValue(e.getClickedBlock().getX()))) { return; } 
		if (!(b.y.containsValue(e.getClickedBlock().getY()))) { return; }
		if (!(b.z.containsValue(e.getClickedBlock().getZ()))) { return; }
		
		// Check if block is registered in maps
		for (String get : b.infoheads) {
			if (!(b.x.get(get) == e.getClickedBlock().getX())) { continue;}
			if (!(b.y.get(get) == e.getClickedBlock().getY())) { continue;}
			if (!(b.z.get(get) == e.getClickedBlock().getZ())) { continue;}
			
			String message = b.messages.get(get);
			String command = b.commands.get(get);
			
			// Dispatch command and message!
			if (!(command.length() == 0)) {
				b.getServer().dispatchCommand(b.getServer().getConsoleSender(), placeHolderMessage(command, e.getPlayer(), e));
			}
			if (!(message.length() == 0)) {
				Utils.sendMessage(e.getPlayer(), placeHolderMessage(message, e.getPlayer(), e));
			}	
		}	
	}
	
	/**
	 * Replaces all placeholders in the messages / commands
	 * 
	 * @param message
	 * @param player
	 * @param e
	 * @return msg The new message
	 */
	
	private String placeHolderMessage(String message, Player player, PlayerInteractEvent e) {
		//{player-x} {player-y} {player-z} {block-x} {block-y} {block-z} {player-name}
		String msg = message;
		// Player Variables
		msg = msg.replace("{player-x}", "" + player.getLocation().getBlockX()); // "" so it validates as a string
		msg = msg.replace("{player-y}", "" + player.getLocation().getBlockY());
		msg = msg.replace("{player-z}", "" + player.getLocation().getBlockZ());
		msg = msg.replace("{player-name}", player.getName().toString());
		
		// Block Variables
		msg = msg.replace("{block-x}", "" + e.getClickedBlock().getX());
		msg = msg.replace("{block-y}", "" + e.getClickedBlock().getY());
		msg = msg.replace("{block-z}", "" + e.getClickedBlock().getZ());
		
		return msg;
	}
	/**
	 * Add the name to the registered list in config.yml
	 * 
	 * @param name
	 */
	
	private void addToList(String name) {
		for (String s : b.getConfig().getStringList("Names")) {
			names.add(s);
		}
		names.add(name);
		b.getConfig().set("Names", names);
	}
}
