package me.harry0198.infoheads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.harry0198.infoheads.commands.general.conversations.InfoHeadsConversationPrefix;
import me.harry0198.infoheads.commands.general.conversations.NamePrompt;
import me.harry0198.infoheads.inventorys.Inventory;
import me.harry0198.infoheads.listeners.EntityListeners;
import me.harry0198.infoheads.utils.Constants;
import net.milkbowl.vault.permission.Permission;

public class InfoHeads extends JavaPlugin implements CommandExecutor, ConversationAbandonedListener {

    // Creation process Lists & Maps
    public Map<Player, String> name = new HashMap<>();
    public List<Player> namedComplete = new ArrayList<>();

    // Data Storage lists & Maps
    public List<String> infoheads = new ArrayList<>();
    public Map<String, Integer> x = new HashMap<>();
    public Map<String, Integer> y = new HashMap<>();
    public Map<String, Integer> z = new HashMap<>();
    public Map<String, String> messages = new HashMap<>();
    public Map<String, String> commands = new HashMap<>();

    //Interfaces
    public VersionInterface versionHandler;

    // Inventory Storage
    public Map<UUID, ItemStack[]> items = new HashMap<>();
    public Map<UUID, ItemStack[]> armor = new HashMap<>();

    // Vault
    private static Permission perms = null;

    // Conversations
    private ConversationFactory conversationFactory;

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

    @Override
    public void onEnable() {

        setupPermissions();
        setupVersions();
        // Metrics
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new EntityListeners(this), this);

        infoHeadsData();
        getCommand("infoheads").setExecutor(this);

    }

    /**
     * Stores the value in a list to prevent constant checking from config file.
     * (More efficient)
     */

    public void infoHeadsData() {
        infoheads.clear();

        // Add names to a List
        for (String s : getConfig().getStringList("Names")) {
            infoheads.add(s);
        }
        // Add names' data to a map
        for (String s : infoheads) {
            x.put(s, getConfig().getInt(s + ".x"));
            y.put(s, getConfig().getInt(s + ".y"));
            z.put(s, getConfig().getInt(s + ".z"));
            messages.put(s, getConfig().getString(s + ".message"));
            commands.put(s, getConfig().getString(s + ".command"));
        }

    }

    /**
     * Vault API Hook
     *
     * @return perms
     */
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
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
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new NamePrompt(this))
                .withEscapeSequence("/quit").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(this);
    }

    /**
     * WILL BE MOVED TO COMMANDS & HANDLERS
     *
     * @param sender
     * @param cmd
     * @param s
     * @param args
     * @return
     */
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Conversable) {
            if (getPermissions().playerHas((Player) sender, Constants.ADMIN_PERM) == true) {
                if (args.length == 0) {
                    createCommand(sender, cmd, s, args);
                }
                if (args.length == 1) {
                    if (args[1].equals("edit")) {
                        editCommand(sender, cmd, s, args);
                    }
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
        return;

    }

    private void editCommand(CommandSender sender, Command cmd, String s, String[] args) {

    }

    private void setupVersions() {
        String version;

        if (Bukkit.getVersion().contains("1.8")) {
            version = "V1_8";
        } else if (Bukkit.getVersion().contains("1.9")
                || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11")
                || Bukkit.getVersion().contains("1.12")) {
            version = "legacy"; // Legacy = 1.9 > 1.12.2
        } else if (Bukkit.getVersion().contains("1.13")|| Bukkit.getVersion().contains("1.14")) {
        version = "V1_13";

        } else {
            version = "failure";
            info("InfoHeads: ");
            severe("onEnable version check failure. ");
            severe("Your server version is not available for this plugin. ");
            severe("This plugin only supports clients 1.8.8-1.14 ");
        }
        // Get the last element of the package

        try {
            final Class<?> clazz = Class.forName("me.harry0198.infoheads." + version + ".VersionHandler");
            // Checking for Version interface
            if (VersionInterface.class.isAssignableFrom(clazz)) { // Make sure it's going to implement it
                this.versionHandler = (VersionInterface) clazz.getConstructor().newInstance(); // Set handler
            }
        } catch (final Exception e) {
            e.printStackTrace();
            severe("Could not find support for this Version.");
            setEnabled(false);
        }
    }


}
