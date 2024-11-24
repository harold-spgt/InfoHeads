package me.harry0198.infoheads.core;

import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.config.Configuration;
import me.harry0198.infoheads.core.config.ConfigurationService;
import me.harry0198.infoheads.core.config.LocalizedMessageService;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.handlers.*;
import me.harry0198.infoheads.core.hooks.PlaceholderHandlingStrategy;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.persistence.repository.Repository;
import me.harry0198.infoheads.core.persistence.repository.RepositoryFactory;
import me.harry0198.infoheads.core.service.InfoHeadService;
import me.harry0198.infoheads.core.service.UserStateService;
import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.function.UnaryOperator;

public abstract class InfoHeadsPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final Path workingDirectory;
    private InfoHeadService infoHeadService;
    private UserStateService userStateService;
    private LocalizedMessageService localizedMessageService;
    private CommandHandler commandHandler;
    private ConfigurationService configurationService;

    protected InfoHeadsPlugin(Path workingDirectory) {
        if (workingDirectory == null) throw new IllegalArgumentException("Working directory must not be null");
        this.workingDirectory = workingDirectory;
    }

    public abstract void registerEventHandlers(
            BreakHandler breakHandler,
            InteractHandler interactHandler,
            PlaceHandler placeHandler,
            PlayerJoinHandler playerJoinHandler,
            PlayerQuitHandler playerQuitHandler
    );
    public abstract void registerCommandExecutors(CommandHandler commandHandler);
    public abstract void unregisterAll();
    public abstract UnaryOperator<String> getColourReplaceStrategy();
    public abstract PlaceholderHandlingStrategy getPlaceholderHandlingStrategy();
    public abstract void runUpdateNotifier();
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

    public void onDisable() {
        // Write cache to file and block until complete.
        infoHeadService.saveCacheToRepository().join();
    }

    public void reload() {
        onDisable();
        unregisterAll();
        load();
    }

    public void load() {
        EventDispatcher eventDispatcher = EventDispatcher.getInstance();
        this.configurationService = new ConfigurationService(workingDirectory);

        // Reloading / Loading is a synchronous task, we could have mismatched DI if we do this off thread... so join.
        this.configurationService.getConfigInitializationProcedure().join();
        this.configurationService.getConfiguration().ifPresent(config -> LoggerFactory.getLogger().setLevel(config.getLoggingLevel()));
        this.userStateService = new UserStateService();
        Repository<InfoHeadProperties> repository = RepositoryFactory.getRepository(workingDirectory);

        // Initialize new InfoHeadService.
        this.infoHeadService = new InfoHeadService(repository);
        this.localizedMessageService = generateLocalizedMessageService(workingDirectory, getColourReplaceStrategy());

        this.commandHandler = new CommandHandler(this, infoHeadService, userStateService, localizedMessageService, EventDispatcher.getInstance());
        registerCommandExecutors(commandHandler);

        registerEventHandlers(
                new BreakHandler(infoHeadService, localizedMessageService, eventDispatcher),
                new InteractHandler(infoHeadService, localizedMessageService, getPlaceholderHandlingStrategy(), eventDispatcher),
                new PlaceHandler(infoHeadService, userStateService, localizedMessageService, eventDispatcher),
                new PlayerJoinHandler(eventDispatcher),
                new PlayerQuitHandler(eventDispatcher)
        );
    }

    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    public InfoHeadService getInfoHeadService() {
        return infoHeadService;
    }

    public LocalizedMessageService getLocalizedMessageService() {
        return localizedMessageService;
    }

    public UserStateService getUserStateService() {
        return userStateService;
    }

    private LocalizedMessageService generateLocalizedMessageService(Path workingDirectory, UnaryOperator<String> colourReplaceStrategy) {
        Optional<Configuration> configuration = this.configurationService.getConfiguration();

        Locale locale;
        if (configuration.isEmpty()) {
            LOGGER.warn("Using default language tag en-GB - configuration was not loaded.");
            locale = Locale.forLanguageTag("en-GB");
        } else {
            LOGGER.debug(String.format("Using language tag (%s)", configuration.get().getLanguageTag()));
            locale = Locale.forLanguageTag(configuration.get().getLanguageTag());
        }

        return new LocalizedMessageService(locale, workingDirectory, colourReplaceStrategy);
    }
}
