package com.haroldstudios.infoheads;

import com.haroldstudios.infoheads.commands.Commands;
import com.haroldstudios.infoheads.components.hooks.HdbListener;
import com.haroldstudios.infoheads.conversations.*;
import com.haroldstudios.infoheads.datastore.DataStore;
import com.haroldstudios.infoheads.elements.ElementType;
import com.haroldstudios.infoheads.listeners.HeadInteract;
import com.haroldstudios.infoheads.components.hooks.HdbHook;
import com.haroldstudios.infoheads.listeners.HeadPlace;
import com.haroldstudios.infoheads.listeners.PlayerJoin;
import com.haroldstudios.infoheads.serializer.FileUtil;
import com.haroldstudios.infoheads.utils.UpdateChecker;
import lombok.Getter;
import me.mattstudios.mf.base.CommandManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Level;

//TODO InfoHeads -> Cooldowns in the modular gui. Reload issue is already fixed. Fix that bug where it would spew edit item if menu is exit, implemented but test it works!.
public final class InfoHeads extends JavaPlugin {

    @Getter private DataStore dataStore;
    @Getter private Commands commands;

    @Getter private FileUtil fileUtil;

    /* Hooks */
    public HdbHook hdb;
    public boolean papi = false;
    public boolean blockParticles = false;

    @Override
    public void onEnable() {

        load();
        Bukkit.getScheduler().runTaskAsynchronously(this, UpdateChecker::checkForUpdate);
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this);

        CommandManager cm = new CommandManager(this);
        commands = new Commands(this);
        cm.register(commands);
        cm.hideTabComplete(true);

        if (packagesExists("org.bukkit.util.Consumer"))
            getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    public void load() {
        saveDefaultConfig();

        this.fileUtil = new FileUtil();

        this.dataStore = getFileUtil().getFile(DataStore.class).exists() ? getFileUtil().load(DataStore.class) : new DataStore();

        // InfoHead, (UUID, TimeStamp)

        dataStore.getInfoHeads().values().forEach(configuration -> {

            Set<Map.Entry<UUID, Long>> entrySet = configuration.getTimestamps().entrySet();

            Iterator<Map.Entry<UUID, Long>> iterator = entrySet.iterator();

            while (iterator.hasNext()) {
                Map.Entry<UUID, Long> entry = iterator.next();
                Long timestamp = entry.getValue();

                if (timestamp < System.currentTimeMillis()) {
                    iterator.remove();
                }
            }
        });


        fileUtil.save(this.dataStore);

        DataStore.placerMode.clear();
        getServer().getPluginManager().registerEvents(new HeadInteract(this), this);
        getServer().getPluginManager().registerEvents(new HeadPlace(this), this);
        if (packagesExists("me.arcaniax.hdb.api.DatabaseLoadEvent", "me.arcaniax.hdb.api.HeadDatabaseAPI"))
            getServer().getPluginManager().registerEvents(new HdbListener(this), this);
        if (packagesExists("me.clip.placeholderapi.PlaceholderAPI"))
            papi = true;
        if (packagesExists("me.badbones69.blockparticles.Methods"))
            blockParticles = true;
    }

    @Override
    public void onDisable() {
        fileUtil.save(dataStore);
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

    public void debug(String msg) {
        getLogger().log(Level.INFO, msg);
    }

    public ConversationFactory getInputFactory(final InfoHeadConfiguration infoHeadConfiguration, final ElementType element) {
        return new ConversationFactory(this).withModality(true)
                .withPrefix(new InfoHeadsConversationPrefix()).withFirstPrompt(new ElementValueInput(infoHeadConfiguration, element))
                .withEscapeSequence("cancel").withTimeout(60)
                .thatExcludesNonPlayersWithMessage("Console is not supported by this command");
    }


    public static InfoHeads getInstance() {
        return getPlugin(InfoHeads.class);
    }

    public void warn(String msg) {
        getLogger().log(Level.WARNING, msg);
    }

    public void info(String msg) {
        getLogger().log(Level.INFO, msg);
    }

    public void error(String msg) {
        getLogger().log(Level.SEVERE, msg);
    }
}
