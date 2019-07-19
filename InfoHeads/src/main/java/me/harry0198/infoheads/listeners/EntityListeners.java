package me.harry0198.infoheads.listeners;

import me.harry0198.infoheads.utils.LoadedLocations;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.harry0198.infoheads.InfoHeads;
import me.harry0198.infoheads.inventorys.Inventory;
import me.harry0198.infoheads.utils.Constants;
import me.harry0198.infoheads.utils.Utils;
import org.bukkit.inventory.EquipmentSlot;

public class EntityListeners implements Listener {
	
	private InfoHeads infoHeads;
	private boolean offHand;

	public EntityListeners(InfoHeads infoHeads, boolean offHand) {
		this.infoHeads = infoHeads;
		this.offHand = offHand;

	}
	
	/**
	 * Triggered pass after player hits 'PlaceBlockPrompt' and
	 * they're added to the map for naming
	 * 
	 * @param e BlockPlaceEvent
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!(infoHeads.namedComplete.contains(e.getPlayer()))) return;
		Utils.sendMessage(e.getPlayer(), "&aInfoHeads Wizard: &fReceived Placement!");

		World world = e.getPlayer().getWorld();
		int x = e.getBlockPlaced().getX();
		int y = e.getBlockPlaced().getY();
		int z = e.getBlockPlaced().getZ();

		infoHeads.getConfig().set("Infoheads." + (infoHeads.keys + 1) + ".location.x", x);
		infoHeads.getConfig().set("Infoheads." + (infoHeads.keys + 1) + ".location.y", y);
		infoHeads.getConfig().set("Infoheads." + (infoHeads.keys + 1) + ".location.z", z);
		infoHeads.getConfig().set("Infoheads." + (infoHeads.keys + 1) + ".location.world", world.getName());
		infoHeads.saveConfig();

		addToList(world);
		
		new Inventory().restoreInventory(e.getPlayer());
		infoHeads.namedComplete.remove(e.getPlayer());

		Utils.sendMessage(e.getPlayer(), "&a[Wizard] &fCreation complete!");
	 	infoHeads.setup();
	}
	
	/**
	 * Triggered Every time a player interacts with something
	 * 
	 * @param e PlayerInteractEvent
	 */
	
	@EventHandler
	public void onInteractEvent(PlayerInteractEvent e) {
		// Check for one click depending on ver
        if (offHand) {
			if (e.getHand() == EquipmentSlot.OFF_HAND) return;
		}
		try {
			e.getClickedBlock().getLocation();
		} catch (NullPointerException npe) {
			return;
		}
        // Perm check
		if (!(InfoHeads.getPerms().playerHas(e.getPlayer(), Constants.BASE_PERM + "use"))) { return;}
		// Check if they've clicked a valid block


		// Check if block is registered in maps
		for (LoadedLocations each : infoHeads.getLoadedLoc()) {
			if (!(each.getLocation().equals(e.getClickedBlock().getLocation()))) continue;
			Player player = e.getPlayer();

			if (each.getCommands() != null) {
				for (String cmds : each.getCommands())
					infoHeads.getServer().dispatchCommand(infoHeads.getServer().getConsoleSender(), placeHolderMessage(cmds, player, e));
			}

			if (each.getMessages() != null) {
				for (String msg : each.getMessages())
					Utils.sendMessage(player, placeHolderMessage(msg, player, e), false);

			}
		}
	}

	//TODO Refine this
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (!(InfoHeads.getPerms().playerHas(e.getPlayer(), Constants.ADMIN_PERM))) { return;}
		if (!infoHeads.checkLocationExists(e.getBlock().getLocation(), e.getPlayer())) return;
		infoHeads.deleteInfoHead(e.getBlock().getLocation());
		Utils.sendMessage(e.getPlayer(), "InfoHead deleted");
	}
	
	/**
	 * Replaces all placeholders in the messages / commands
	 * 
	 * @param msg String passed through to decode
	 * @param player Player Entity
	 * @param e PlayerInteractEvent
	 * @return msg The new message
	 */
	private String placeHolderMessage(String msg, Player player, PlayerInteractEvent e) {
		//{player-x} {player-y} {player-z} {block-x} {block-y} {block-z} {player-name}
		// Player Variables
		msg = msg.replace("{player-x}", "" + player.getLocation().getBlockX()); // "" so it validates as a string
		msg = msg.replace("{player-y}", "" + player.getLocation().getBlockY());
		msg = msg.replace("{player-z}", "" + player.getLocation().getBlockZ());
		msg = msg.replace("{player-name}", player.getName());
		
		// Block Variables
		msg = msg.replace("{block-x}", "" + e.getClickedBlock().getX());
		msg = msg.replace("{block-y}", "" + e.getClickedBlock().getY());
		msg = msg.replace("{block-z}", "" + e.getClickedBlock().getZ());

		if (infoHeads.papi)
			msg = infoHeads.papiMethod.execute(player, msg);
		
		return msg;
	}

	// Adds to builder class
	private void addToList(World world) {
		ConfigurationSection section = infoHeads.getConfig().getConfigurationSection("Infoheads");

		String key = (infoHeads.keys + 1) + "";

		int x = section.getInt(key + ".location.x");
		int y = section.getInt(key + ".location.y");
		int z = section.getInt(key + ".location.z");

		infoHeads.getLoadedLoc().add(new LoadedLocations.Builder()
				.setLocation(new Location(world, x, y, z))
				.setMessage(section.getStringList(key + "messages"))
				.setCommand(section.getStringList(key + "commands"))
				.build());

	}
}
