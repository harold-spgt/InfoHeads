package me.harry0198.infoheads.core;

import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.service.ConfigurationService;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;

public interface InfoHeadsPlugin {

    void registerEventHandlers();
    void registerCommandExecutors(CommandHandler commandHandler);
    void unregisterAll();
    void runUpdateNotifier();

    InfoHeadService getInfoHeadService();

    void onEnable();
    void onDisable();
    void load();
    void reload();
//    void onEnable() {
//        load();
//
//        var configurationOptional = this.configurationService.getConfiguration();
//        if (configurationOptional.isPresent()) {
//            if (configurationOptional.get().isCheckForUpdate()) {
//                runUpdateNotifier();
//            }
//        } else {
//            runUpdateNotifier();
//        }
//
//    }

//    public void onDisable() {
//        // Write cache to file and block until complete.
//        infoHeadService.saveCacheToRepository().join();
//    }
//
//    public void reload() {
//        onDisable();
//        unregisterAll();
//        load();
//    }
//
//    public void load() {
//        // Reloading / Loading is a synchronous task, we could have mismatched DI if we do this off thread... so join.
//        this.configurationService.getConfigInitializationProcedure().join();
//        this.configurationService.getConfiguration().ifPresent(config -> LoggerFactory.getLogger().setLevel(config.getLoggingLevel()));
//
//        registerCommandExecutors(commandHandler);
//
//        registerEventHandlers();
//    }
}
