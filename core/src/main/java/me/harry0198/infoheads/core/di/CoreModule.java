package me.harry0198.infoheads.core.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import jakarta.inject.Qualifier;
import me.harry0198.infoheads.core.InfoHeadsPlugin;
import me.harry0198.infoheads.core.commands.CommandHandler;
import me.harry0198.infoheads.core.config.Configuration;
import me.harry0198.infoheads.core.service.*;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.persistence.repository.LocalRepository;
import me.harry0198.infoheads.core.persistence.repository.Repository;

import java.lang.annotation.Retention;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.function.UnaryOperator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessageService.class).to(LocalizedMessageService.class);
//        bind(MyService.class).to(MyServiceImpl.class);
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface WorkingDirectory {}

    @Provides
    static ConfigurationService provideConfigurationService(@WorkingDirectory Path workingDirectory) {
        return new ConfigurationService(workingDirectory);
    }

    @Provides
    static Locale provideLocale(ConfigurationService configurationService) {
        Optional<Configuration> configuration = configurationService.getConfiguration();

        Locale locale;
        if (configuration.isEmpty()) {
            //LOGGER.warn("Using default language tag en-GB - configuration was not loaded.");
            locale = Locale.forLanguageTag("en-GB");
        } else {
//            LOGGER.debug(String.format("Using language tag (%s)", configuration.get().getLanguageTag()));
            locale = Locale.forLanguageTag(configuration.get().getLanguageTag());
        }

        return locale;
    }

    @Provides
    static InfoHeadService provideInfoHeadService(Repository<InfoHeadProperties> repository) {
        return new InfoHeadService(repository);
    }

    @Provides
    static Repository<InfoHeadProperties> provideRepository(@WorkingDirectory Path workingDirectory) {
        return new LocalRepository<>(workingDirectory.resolve("heads"), InfoHeadProperties.class);
    }

    @Provides
    static UserStateService provideUserStateService() {
        return new UserStateService();
    }

    @Provides
    static EventDispatcher provideEventDispatcher() {
        return new EventDispatcher();
    }
}
