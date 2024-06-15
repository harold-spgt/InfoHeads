package me.harry0198.infoheads.spigot;


import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.handlers.*;
import me.harry0198.infoheads.core.model.InfoHeadProperties;
import me.harry0198.infoheads.core.repository.Repository;
import me.harry0198.infoheads.core.repository.RepositoryFactory;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.UpdateChecker;
import me.harry0198.infoheads.spigot.commands.BukkitCmdExecutor;
import me.harry0198.infoheads.spigot.listener.BukkitEventListener;
import me.harry0198.infoheads.spigot.listener.InfoHeadEventHandlerRegister;
import me.harry0198.infoheads.spigot.ui.InventoryGuiListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public final class InfoHeads extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger(InfoHeads.class.getName());
    public boolean papi = false;
    public boolean blockParticles = false;

    @Override
    public void onEnable() {
        load();
        new Metrics(this, 4607);

        Locale locale = Locale.forLanguageTag("en-GB");
        LocalizedMessageService localizedMessageService = new LocalizedMessageService(locale);

        Repository<InfoHeadProperties> repository = RepositoryFactory.getRepository();

        InfoHeadService infoHeadService = new InfoHeadService(repository);
        CommandHandler commandHandler = new CommandHandler(infoHeadService, localizedMessageService, EventDispatcher.getInstance());

        // Register event listeners.
        new InfoHeadEventHandlerRegister();

        this.getCommand("infoheads").setExecutor(new BukkitCmdExecutor(commandHandler));

        getServer().getPluginManager().registerEvents(new InventoryGuiListener(), this);
        getServer().getPluginManager().registerEvents(new BukkitEventListener(
                new BreakHandler(infoHeadService, localizedMessageService),
                new InteractHandler(infoHeadService, localizedMessageService),
                new PlaceHandler(infoHeadService, localizedMessageService),
                new PlayerJoinHandler(),
                new PlayerQuitHandler()
        ), this);

//        api = new InfoHeadsImpl();
//        getServer().getServicesManager().register(InfoHeadsApi.class,api,this, ServicePriority.Normal);

        (new UpdateChecker(67080)).getVersion((version) -> {
            if (InfoHeads.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
                LOGGER.info("There is no new update available.");
            } else {
                LOGGER.info("There is a new update available. Version: " + version);
            }
        });
    }

    public void load() {
//        if (!messagesFile.exists()) {
//            saveResource("messages.yml", true);
//            messagesFile.getParentFile().mkdirs();
//            try {
//                messagesFile.createNewFile();
//                debug("Creating a messages.yml file");
//            } catch (IOException e) {
//                error("Cannot create messages.yml configuration file.");
//            }
//        }
//
//        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
//        updateMessagesConfig();
//        saveDefaultConfig();
//
//        this.fileUtil = new FileUtil();
//
//        this.dataStore = getFileUtil().getFile(DataStore.class).exists() ? getFileUtil().load(DataStore.class) : new DataStore();

        // InfoHead, (UUID, TimeStamp)
        // Checks and removes people who's time has elapsed the cooldown from map.


//        fileUtil.save(this.dataStore);

//        DataStore.placerMode.clear();
//        if (packagesExists("me.clip.placeholderapi.PlaceholderAPI"))
//            papi = true;
//        if (packagesExists("me.badbones69.blockparticles.Methods"))
//            blockParticles = true;
    }

    @Override
    public void onDisable() {
        //TODO
//        fileUtil.save(dataStore);
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

//    public static ConversationFactory getInputFactory(final InfoHeadConfiguration infoHeadConfiguration, final Element.InfoHeadType element) {
//        return new ConversationFactory(InfoHeads.getInstance())
//                .withModality(true)
//                .withPrefix(new InfoHeadsConversationPrefix())
//                .withFirstPrompt(new ElementValueInput(InfoHeads.getInstance().dataStore, infoHeadConfiguration, element)) //TODO incorrect static use here. Datstore should be singleton? no. Should use interface
//                .withEscapeSequence("cancel")
//                .withTimeout(60)
//                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
//    }

    public static InfoHeads getInstance() {
        return getPlugin(InfoHeads.class);
    }
}
