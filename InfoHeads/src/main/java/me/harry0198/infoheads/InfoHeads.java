package me.harry0198.infoheads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.harry0198.infoheads.commands.general.conversations.CommandPrompt;
import me.harry0198.infoheads.commands.player.PlayerCmd;
import me.harry0198.infoheads.inventorys.HeadStacks;
import me.harry0198.infoheads.inventorys.Inventory;
import me.harry0198.infoheads.utils.LoadedLocations;
import me.harry0198.infoheads.utils.PapiMethod;
import me.harry0198.infoheads.utils.Utils;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.harry0198.infoheads.commands.general.conversations.InfoHeadsConversationPrefix;
import me.harry0198.infoheads.listeners.EntityListeners;
import net.milkbowl.vault.permission.Permission;

public class InfoHeads extends JavaPlugin implements ConversationAbandonedListener {

    public List<Player> namedComplete = new ArrayList<>();
    private List<LoadedLocations> loadedLoc = new ArrayList<>();
    public HeadStacks headStacks = new HeadStacks();
    public PapiMethod papiMethod = new PapiMethod();
    public boolean papi = false;
    public Map<Player, String> uuid = new HashMap<>();


    // Data Storage lists & Maps
    public List<String> infoheads = new ArrayList<>();

    // Inventory Storage
    public Map<UUID, ItemStack[]> items = new HashMap<>();
    public Map<UUID, ItemStack[]> armor = new HashMap<>();
    private boolean offHand = true;

    // Vault
    private static Permission perms = null;

    // Conversations
    private ConversationFactory conversationFactory;

    /**
     * Class constructor -- loads the conversation factory
     */
    public InfoHeads() {
        this.conversationFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new CommandPrompt())
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(this);
    }

    @Override
    public void onEnable() {
        // Checking for PAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            papi = true;

        setupPermissions();
        // Metrics
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this);

        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();

        setup();
        if (Bukkit.getServer().getVersion().contains("1.8")) offHand = false;

        getServer().getPluginManager().registerEvents(new EntityListeners(this, offHand), this);

        getCommand("infoheads").setExecutor(new PlayerCmd());
    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }

    /**
     * Loads all of the config values into the Builder class 'LoadedLocations'
     * Also sets how many sub sections the config file has where subsections = '0:', '1:' etc.
     */
    public void setup() {
        loadedLoc.clear();

        ConfigurationSection section = getConfig().getConfigurationSection("Infoheads");
        for (String each : section.getKeys(false)) {

            World world = Bukkit.getWorld(section.getString(each + ".location.world"));
            int x = section.getInt(each + ".location.x");
            int y = section.getInt(each + ".location.y");
            int z = section.getInt(each + ".location.z");

            loadedLoc.add(new LoadedLocations.Builder()
                    .setLocation(new Location(world, x, y, z))
                    .setCommand(section.getStringList(each + ".commands"))
                    .setMessage(section.getStringList(each + ".messages"))
                    .setKey(each)
                    .build());
        }
    }

    public void reloadCommand() {
        this.reloadConfig();

        setup();
        BlockPlaceEvent.getHandlerList().unregister(this);
        PlayerInteractEvent.getHandlerList().unregister(this);
        getServer().getPluginManager().registerEvents(new EntityListeners(this, offHand), this);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean checkLocationExists(Location location) {
        for (LoadedLocations loc : getInstance().getLoadedLoc()) {
            if (location.equals(loc.getLocation())) return true;
        }
        return false;
    }

    public void deleteInfoHead(Location location) {
        for (LoadedLocations loc : getInstance().getLoadedLoc()) {
            if (location.equals(loc.getLocation())) {
                getInstance().getConfig().set("Infoheads." + loc.getKey(), null);
                getInstance().saveConfig();
                getInstance().setup();
                return;
            }
        }
    }

    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
        Player player = (Player) abandonedEvent.getContext().getForWhom();
        // If exit using abandon keyword
        if (!(abandonedEvent.gracefulExit())) {
            Utils.sendMessage(player, "Exit the Infoheads wizard.");
            new Inventory().restoreInventory(player);
        }
    }

    public static Permission getPerms() {
        return perms;
    }

    public ConversationFactory getConversationFactory() {
        return conversationFactory;
    }

    public List<LoadedLocations> getLoadedLoc() {
        return loadedLoc;
    }

    public static InfoHeads getInstance() {
        return getPlugin(InfoHeads.class);
    }

}
