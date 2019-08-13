package me.harry0198.infoheads;

import java.util.*;
import java.util.stream.Stream;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.harry0198.infoheads.commands.CommandManager;
import me.harry0198.infoheads.commands.Commands;
import me.harry0198.infoheads.commands.general.conversations.editspecific.LineSelectPrompt;
import me.harry0198.infoheads.commands.general.conversations.wizard.CommandPrompt;
import me.harry0198.infoheads.commands.player.EditCommand;
import me.harry0198.infoheads.guice.BinderModule;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.harry0198.infoheads.commands.general.conversations.wizard.InfoHeadsConversationPrefix;
import me.harry0198.infoheads.listeners.EntityListeners;
import org.bukkit.scheduler.BukkitRunnable;

public class InfoHeads extends JavaPlugin implements ConversationAbandonedListener { //TODO API -> ex: getHeadAtLocation()
    // Array to check if naming / assignment is complete in wizard
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
    public boolean offHand = true;
    @Inject private CommandManager commandManager;
    private Injector injector;
    private Map<Player, EditCommand.Types> typesMap = new HashMap<>();
    public Map<Player, LoadedLocations> typesMapClass = new HashMap<>();

    // Conversations
    private ConversationFactory conversationFactory;
    private ConversationFactory editFactory;

    /**
     * Class constructor -- loads the conversation factory
     */
    public InfoHeads() {
        this.conversationFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new CommandPrompt())
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(this);
        this.editFactory = new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new LineSelectPrompt())
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command")
                .addConversationAbandonedListener(this);
    }

    @Override
    public void onEnable() {
        // Metrics
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this);
        // Checking for PAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            papi = true;

        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveDefaultConfig();

        Stream.of(Registerables.GUICE, Registerables.INFOHEADS, Registerables.LISTENERS, Registerables.COMMANDS).forEach(this::register);

        if (Bukkit.getServer().getVersion().contains("1.8")) offHand = false;
    }

    public void register(Registerables registerable) {
        switch (registerable) {
            case GUICE:
                injector = new BinderModule(this).createInjector();
                injector.injectMembers(this);
                break;

            case COMMANDS:
                Objects.requireNonNull(getCommand("infoheads"))
                        .setExecutor(commandManager);
                Arrays.stream(Commands.values()).map(Commands::getClazz).map(injector::getInstance).forEach(commandManager.getCommands()::add);
                break;

            case INFOHEADS:
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
                break;

            case LISTENERS:
                getServer().getPluginManager().registerEvents(new EntityListeners(this, offHand, commandManager), this);
                break;
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

    public ConversationFactory getConversationFactory() {
        return conversationFactory;
    }
    public ConversationFactory getEditFactory() {
        return editFactory;
    }
    public Map<Player, EditCommand.Types> getCurrentEditType() { return typesMap; }
    public List<LoadedLocations> getLoadedLoc() {
        return loadedLoc;
    }

    public static InfoHeads getInstance() {
        return getPlugin(InfoHeads.class);
    }

    public enum Registerables {
        COMMANDS, INFOHEADS, LISTENERS, GUICE
    }

}
