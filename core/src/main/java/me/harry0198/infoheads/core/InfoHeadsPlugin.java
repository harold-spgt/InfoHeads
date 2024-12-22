package me.harry0198.infoheads.core;

import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.event.InfoHeadEventListenerRegister;
import me.harry0198.infoheads.core.service.ConfigurationService;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;

import java.util.Objects;

public abstract class InfoHeadsPlugin implements Plugin {

    private final ConfigurationService configurationService;
    private final InfoHeadService infoHeadService;
    private final CommandHandler commandHandler;
    private final InfoHeadEventListenerRegister infoHeadEventListenerRegister;

    protected InfoHeadsPlugin(
            ConfigurationService configurationService,
            InfoHeadService infoHeadService,
            CommandHandler commandHandler,
            InfoHeadEventListenerRegister infoHeadEventListenerRegister) {
        this.configurationService = Objects.requireNonNull(configurationService);
        this.infoHeadService = Objects.requireNonNull(infoHeadService);
        this.commandHandler = Objects.requireNonNull(commandHandler);
        this.infoHeadEventListenerRegister = Objects.requireNonNull(infoHeadEventListenerRegister);
    }

    public abstract void registerEventHandlers();

    public abstract void registerCommandExecutors(CommandHandler commandHandler);

    public abstract void unregisterAll();

    public abstract void runUpdateNotifier();

    @Override
    public void onEnable() {
        load();

        var configurationOptional = this.configurationService.getConfiguration();
        if (configurationOptional.isPresent()) {
            if (configurationOptional.get().isCheckForUpdate()) {
                runUpdateNotifier();
            }
        } else {
            runUpdateNotifier();
        }
    }

    @Override
    public void onDisable() {
        // As we often update the cache object instead of writing for every interaction, we can do a one time cleanup.
        this.infoHeadService.saveCacheToRepository();
        unregisterAll();
    }

    public void reload() {
        onDisable();
        load();
    }

    public void load() {
        // Reloading / Loading is a synchronous task, we could have mismatched DI if we do this off thread... so join.
        this.configurationService.getConfigInitializationProcedure().join();
        this.configurationService.getConfiguration().ifPresent(config -> LoggerFactory.getLogger().setLevel(config.getLoggingLevel()));

        registerCommandExecutors(commandHandler);

        registerEventHandlers();

        // Register listeners
        this.infoHeadEventListenerRegister.registerGetConsoleCommandInputListener();
        this.infoHeadEventListenerRegister.registerApplyTempPlayerPermissionListener();
        this.infoHeadEventListenerRegister.registerGetCoolDownInputListener();
        this.infoHeadEventListenerRegister.registerGetDelayInputListener();
        this.infoHeadEventListenerRegister.registerGetMessageInputListener();
        this.infoHeadEventListenerRegister.registerGivePlayerHeadsListener();
        this.infoHeadEventListenerRegister.registerGetNameInputListener();
        this.infoHeadEventListenerRegister.registerOpenMenuListener();
        this.infoHeadEventListenerRegister.registerShowInfoHeadListListener();
        this.infoHeadEventListenerRegister.registerGetPlayerCommandInputListener();
        this.infoHeadEventListenerRegister.registerGetPermissionInputListener();
        this.infoHeadEventListenerRegister.registerOpenAddActionMenuListener();
        this.infoHeadEventListenerRegister.registerRemoveTempPermissionListener();
        this.infoHeadEventListenerRegister.registerSendConsoleCommandListener();
        this.infoHeadEventListenerRegister.registerSendPlayerCommandListener();
        this.infoHeadEventListenerRegister.registerSendPlayerMessageListener();
        this.infoHeadEventListenerRegister.registerGetPlayerPermissionInputListener();
    }
}
