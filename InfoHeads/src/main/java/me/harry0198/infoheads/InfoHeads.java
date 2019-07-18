package me.harry0198.infoheads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Stream;

import lombok.Getter;
import me.harry0198.infoheads.commands.general.conversations.CommandPrompt;
import me.harry0198.infoheads.commands.player.PlayerCmd;
import me.harry0198.infoheads.inventorys.HeadStacks;
import me.harry0198.infoheads.utils.LoadedLocations;
import me.harry0198.infoheads.utils.PapiMethod;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.harry0198.infoheads.commands.general.conversations.InfoHeadsConversationPrefix;
import me.harry0198.infoheads.listeners.EntityListeners;
import net.milkbowl.vault.permission.Permission;

public class InfoHeads extends JavaPlugin implements CommandExecutor, ConversationAbandonedListener {

    // Creation process Lists & Maps
    public Map<Player, String> name = new HashMap<>();
    public List<Player> namedComplete = new ArrayList<>();
    public List<LoadedLocations> loadedLoc = new ArrayList<>();
    public HeadStacks headStacks = new HeadStacks();
    public PapiMethod papiMethod = new PapiMethod();
    public Map<Player, Boolean> deleteMode = new HashMap<>(); // in progress
    @Getter
    public boolean papi = false;

    // Data Storage lists & Maps
    public List<String> infoheads = new ArrayList<>();

    // Inventory Storage
    public Map<UUID, ItemStack[]> items = new HashMap<>();
    public Map<UUID, ItemStack[]> armor = new HashMap<>();
    public int keys;
    public boolean offHand = true;
    private EntityListeners entityListeners;

    // Vault
    @Getter
    private static Permission perms = null;

    // Conversations
    @Getter
    public ConversationFactory conversationFactory;


    @Override
    public void onEnable() {

        // Checking for PAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papi = true;
        }
        setupPermissions();
        // Metrics
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this);

        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();

        setup();
        if (Bukkit.getServer().getVersion().contains("1.8")) offHand = false;

        entityListeners = new EntityListeners(this, offHand);

        getServer().getPluginManager().registerEvents(entityListeners, this);

        getCommand("infoheads").setExecutor(new PlayerCmd(this));

    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }

    /**
     * Class constructor -- loads the conversation factory
     */
    public InfoHeads() {
        this.conversationFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new CommandPrompt(this))
                .withEscapeSequence("/quit").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(this);
    }

    public void setup() {
        loadedLoc.clear();
        keys = -1;

        ConfigurationSection section = getConfig().getConfigurationSection("Infoheads");
        for (String each : section.getKeys(false)) {

            World world = Bukkit.getWorld(section.getString(each + ".location.world"));
            int x = section.getInt(each + ".location.x");
            int y = section.getInt(each + ".location.y");
            int z = section.getInt(each + ".location.z");

            loadedLoc.add(LoadedLocations.builder()
                    .location(new Location(world, x, y, z))
                    .command(section.getStringList(each + ".commands"))
                    .message(section.getStringList(each + ".messages"))
                    .build());
            keys = keys + 1;
        }

    }

    public void reloadCommand() {
        this.reloadConfig();

        setup();
        BlockPlaceEvent.getHandlerList().unregister(this);
        PlayerInteractEvent.getHandlerList().unregister(this);
        getServer().getPluginManager().registerEvents(new EntityListeners(this, offHand), this);
    }

    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
        if (abandonedEvent.gracefulExit()) {
            //TODO
        }
    }


    /**
     * Log any message to console with any level.
     *
     * @param level the log level to log on.
     * @param msg   the message to log.
     */
    public void log(Level level, String msg) {
        getLogger().log(level, msg);
    }

    /**
     * Log a message to console on INFO level.
     *
     * @param msg the msg you want to log.
     */
    public void info(String msg) {
        log(Level.INFO, msg);
    }

    /**
     * Log a message to console on WARNING level.
     *
     * @param msg the msg you want to log.
     */
    public void warn(String msg) {
        log(Level.WARNING, msg);
    }

    /**
     * Log a message to console on SEVERE level.
     *
     * @param msg the msg you want to log.
     */
    public void severe(String msg) {
        log(Level.SEVERE, msg);
    }

}
