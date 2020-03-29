package me.harry0198.infoheads;

import me.harry0198.infoheads.commands.Commands;
import me.harry0198.infoheads.components.hooks.HdbHook;
import me.harry0198.infoheads.components.hooks.HdbListener;
import me.harry0198.infoheads.conversations.*;
import me.harry0198.infoheads.datastore.DataStore;
import me.harry0198.infoheads.elements.ConsoleCommandElement;
import me.harry0198.infoheads.elements.Element;
import me.harry0198.infoheads.elements.MessageElement;
import me.harry0198.infoheads.listeners.HeadInteract;
import me.harry0198.infoheads.listeners.HeadPlace;
import me.harry0198.infoheads.listeners.PlayerJoin;
import me.harry0198.infoheads.utils.MessageUtil;
import me.mattstudios.mf.base.CommandManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class InfoHeads extends JavaPlugin {

    private DataStore dataStore;
    private Commands commands;

    /* Hooks */
    public HdbHook hdb;
    public boolean papi = false;

    /* Conversation Factory */
    private ConversationFactory messageFactory;
    private ConversationFactory commandFactory;
    private ConversationFactory playerCommandFactory;
    private ConversationFactory permissionFactory;
    private ConversationFactory delayFactory;

    public void loadConversations() {
        this.messageFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new MessageInput(this))
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(new ConversationAbandoned(this));
        this.commandFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new ConsoleCommandInput(this))
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(new ConversationAbandoned(this));
        this.playerCommandFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new PlayerCommandInput(this))
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(new ConversationAbandoned(this));
        this.permissionFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new PermissionInput(this))
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(new ConversationAbandoned(this));
        this.delayFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new DelayInput(this))
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(new ConversationAbandoned(this));
    }

    @Override
    public void onEnable() {

        load();
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this);

        CommandManager cm = new CommandManager(this);
        commands = new Commands(this);
        cm.register(commands);
        cm.hideTabComplete(true);

        if (packagesExists("org.bukkit.util.Consumer"))
            getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
    }

    public void load() {
        saveDefaultConfig();
        loadConversations();
        this.dataStore = new DataStore(this);
        DataStore.placerMode.clear();
        getServer().getPluginManager().registerEvents(new HeadInteract(this), this);
        getServer().getPluginManager().registerEvents(new HeadPlace(this), this);
        if (packagesExists("me.arcaniax.hdb.api.DatabaseLoadEvent", "me.arcaniax.hdb.api.HeadDatabaseAPI"))
            getServer().getPluginManager().registerEvents(new HdbListener(this), this);
        if (packagesExists("me.clip.placeholderapi.PlaceholderAPI"))
            papi = true;
        updateConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Determines if all packages in a String array are within the Classpath
     * This is the best way to determine if a specific plugin exists and will be
     * loaded. If the plugin package isn't loaded, we shouldn't bother waiting
     * for it!
     * @param packages String Array of package names to check
     * @return Success or Failure
     */
    private static boolean packagesExists(String...packages) {
        try {
            for (String pkg : packages) {
                Class.forName(pkg);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateConfig() {
        if (getConfig().get("config-ver") == null) return;


        if (getConfig().getInt("config-ver") == 1) {
            List<InfoHeadConfiguration> configurations = new ArrayList<>();
            try {
                ConfigurationSection root = getConfig().getConfigurationSection("Infoheads");

                for (String key : root.getKeys(false)) {

                    List<Element> draft = new ArrayList<>();
                    Location location = new Location(Bukkit.getWorld(root.getString(key + ".location.world")), root.getLong(key + ".location.x"), root.getLong(key + ".location.y"), root.getLong(key + ".location.z"));

                    root.getList(key + ".commands").forEach(cmd -> draft.add(new ConsoleCommandElement(cmd.toString())));
                    root.getList(key + ".messages").forEach(msg -> draft.add(new MessageElement(msg.toString())));


                    configurations.add(new InfoHeadConfiguration(location, draft, null));
                    root.set(key, null);
                }

                configurations.forEach(c -> getDataStore().addInfoHead(c));
            } catch (NullPointerException ignore) { }
            getConfig().set("config-ver", 2);
            saveDefaultConfig();
            saveConfig();
            BlockPlaceEvent.getHandlerList().unregister(this);
            PlayerInteractEvent.getHandlerList().unregister(this);
            load();
        }
    }

    public void debug(String msg) {
        getLogger().log(Level.INFO, msg);
    }


    public DataStore getDataStore() {
        return dataStore;
    }

    public ConversationFactory getMessageFactory() {
        return messageFactory;
    }

    public ConversationFactory getCommandFactory() {
        return commandFactory;
    }

    public ConversationFactory getPlayerCommandFactory() {
        return playerCommandFactory;
    }

    public ConversationFactory getPermissionFactory() {
        return permissionFactory;
    }

    public ConversationFactory getDelayFactory() {
        return delayFactory;
    }

    public Commands getCommands() { return commands; }

    public static InfoHeads getInstance() {
        return getPlugin(InfoHeads.class);
    }

    public void warn(String msg) {
        getServer().getLogger().log(Level.WARNING, msg);
    }

    public void info(String msg) {
        getServer().getLogger().log(Level.INFO, msg);
    }
}
