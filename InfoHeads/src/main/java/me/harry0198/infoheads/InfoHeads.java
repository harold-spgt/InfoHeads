package me.harry0198.infoheads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import lombok.Getter;
import me.harry0198.infoheads.commands.general.conversations.CommandPrompt;
import me.harry0198.infoheads.inventorys.HeadStacks;
import me.harry0198.infoheads.utils.LoadedLocations;
import me.harry0198.infoheads.utils.PapiMethod;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.harry0198.infoheads.commands.general.conversations.InfoHeadsConversationPrefix;
import me.harry0198.infoheads.inventorys.Inventory;
import me.harry0198.infoheads.listeners.EntityListeners;
import me.harry0198.infoheads.utils.Constants;
import net.milkbowl.vault.permission.Permission;

public class InfoHeads extends JavaPlugin implements CommandExecutor, ConversationAbandonedListener {

    // Creation process Lists & Maps
    public Map<Player, String> name = new HashMap<>();
    public List<Player> namedComplete = new ArrayList<>();
    public List<LoadedLocations> loadedLoc = new ArrayList<>();
    public HeadStacks headStacks = new HeadStacks();
    public PapiMethod papiMethod = new PapiMethod();
    @Getter
    public boolean papi = false;

    // Data Storage lists & Maps
    public List<String> infoheads = new ArrayList<>();

    // Inventory Storage
    public Map<UUID, ItemStack[]> items = new HashMap<>();
    public Map<UUID, ItemStack[]> armor = new HashMap<>();
    public int keys = -1;

    // Vault
    private static Permission perms = null;

    // Conversations
    private ConversationFactory conversationFactory;


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

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        setup();
        boolean offHand = true;
        if (Bukkit.getServer().getVersion().contains("1.8")) offHand = false;

        getServer().getPluginManager().registerEvents(new EntityListeners(this, offHand), this);

        getCommand("infoheads").setExecutor(this);

    }

    /**
     * Vault API Hook
     *
     */
    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }

    public static Permission getPermissions() {
        return perms;
    }

    /**
     * Command > This section is built using the Conversation API This is good
     * practice compared to AsyncChatEvent as this event can conflict with chat
     * management plugins!
     *
     * It is also much easier imo :)
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

    /**
     * WILL BE MOVED TO COMMANDS & HANDLERS
     *
     * @param sender Sender of command
     * @param cmd Command
     * @param s String
     * @param args Arguments
     * @return boolean
     */
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Conversable) {
            if (getPermissions().playerHas((Player) sender, Constants.ADMIN_PERM)) {
                if (args.length == 0) {
                    createCommand(sender, cmd, s, args);
                }

            } else { sender.sendMessage(ChatColor.RED + "No permission"); }
        } else {
            return false;
        }
        return false;

    }

    public void conversationAbandoned(ConversationAbandonedEvent abandonedEvent) {
        if (abandonedEvent.gracefulExit()) {
        }
    }

    private void createCommand(CommandSender sender, Command cmd, String s, String[] args) {

        Inventory iv = new Inventory(this);

        if (!(Bukkit.getVersion().contains("1.8"))) { // 1.8 clients does not support inventory storage
            iv.storeAndClearInventory((Player) sender);
        }
        iv.infoHeadsInventory((Player) sender);
        conversationFactory.buildConversation((Conversable) sender).begin();

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
