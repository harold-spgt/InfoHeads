package me.harry0198.infoheads.core.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.harry0198.infoheads.core.commands.*;
import me.harry0198.infoheads.core.config.Configuration;
import me.harry0198.infoheads.core.di.annotations.*;
import me.harry0198.infoheads.core.service.*;
import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.persistence.repository.LocalRepository;
import me.harry0198.infoheads.core.persistence.repository.Repository;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;

public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessageService.class).to(LocalizedMessageService.class);
        bind(EventDispatcher.class).in(Singleton.class);
        bind(CmdExecutor.class).annotatedWith(HelpCommandExecutor.class).to(HelpCmdExecutor.class);
        bind(CmdExecutor.class).annotatedWith(ListCommandExecutor.class).to(ListCmdExecutor.class);
        bind(CmdExecutor.class).annotatedWith(ReloadCommandExecutor.class).to(ReloadCmdExecutor.class);
        bind(CmdExecutor.class).annotatedWith(WizardCommandExecutor.class).to(WizardCmdExecutor.class);
        bind(CmdExecutor.class).annotatedWith(RemoveCommandExecutor.class).to(RemoveCmdExecutor.class);
        bind(CmdExecutor.class).annotatedWith(UnknownCommandExecutor.class).to(UnknownCmdExecutor.class);
        bind(CmdExecutor.class).annotatedWith(EditCommandExecutor.class).to(EditCmdExecutor.class);
    }

    @Provides
    @Singleton
    static ConfigurationService provideConfigurationService(@WorkingDirectory Path workingDirectory) {
        return new ConfigurationService(workingDirectory);
    }

    @Provides
    @Singleton
    static Locale provideLocale(ConfigurationService configurationService) {
        Optional<Configuration> configuration = configurationService.getConfiguration();

        Locale locale;
        if (configuration.isEmpty()) {
            locale = Locale.forLanguageTag("en-GB");
        } else {
            locale = Locale.forLanguageTag(configuration.get().getLanguageTag());
        }

        return locale;
    }

    @Provides
    @Singleton
    static InfoHeadService provideInfoHeadService(Repository<InfoHeadProperties> repository) {
        return new InfoHeadService(repository);
    }

    @Provides
    @Singleton
    static Repository<InfoHeadProperties> provideRepository(@WorkingDirectory Path workingDirectory) {
        return new LocalRepository<>(workingDirectory.resolve("heads"), InfoHeadProperties.class);
    }

    @Provides
    @Singleton
    static UserStateService provideUserStateService() {
        return new UserStateService();
    }
}
