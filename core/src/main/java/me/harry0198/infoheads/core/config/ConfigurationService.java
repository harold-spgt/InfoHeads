package me.harry0198.infoheads.core.config;

import me.harry0198.infoheads.core.utils.logging.Level;
import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ConfigurationService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String CONFIGURATION_FILENAME = "config.yml";
    private final Yaml yaml;
    private final CompletableFuture<Void> configInitializeProc;
    private Configuration configuration;
    public ConfigurationService(Path workingDirectory) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Representer representer = new Representer(options);
        representer.getPropertyUtils().setSkipMissingProperties(true);
        representer.addClassTag(Configuration.class, Tag.MAP);

        this.yaml = new Yaml(representer, options);

        File configurationFile = workingDirectory.resolve(CONFIGURATION_FILENAME).toFile();
        if (!configurationFile.exists()) {
            // Write new
            LOGGER.info("Writing configuration to file (" + CONFIGURATION_FILENAME + ")...");
            try {
                Files.createDirectories(workingDirectory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Defaults:
            this.configuration = getDefaultConfiguration();
            this.configInitializeProc = write(configuration, configurationFile);
            return;
        }
        this.configInitializeProc = read(configurationFile).thenAccept((config) -> config.ifPresent(value -> this.configuration = value));
    }

    public CompletableFuture<Void> getConfigInitializationProcedure() {
        return configInitializeProc;
    }

    public CompletableFuture<Void> write(Configuration configuration, File file) {
        return CompletableFuture.runAsync(() -> {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write("""
                        ########################################################
                        #                     InfoHeads                        #
                        # - Authors: Harry0198, Lorenzo0111                    #
                        # - License: FREE! (Well... Actually it's Apache-2.0)  #
                        #                                                      #
                        #                 Enjoy the Plugin!                    #
                        ########################################################
                        #
                        # Optional Dependencies:
                        # PlaceHolderAPI: You can use placeholders provided by PAPI.
                        #
                        # Configuration explanation:
                        # checkForUpdate = If true, enables checking for the latest update on startup.
                        # configVer = Version of generated configuration. (Probably don't touch this).
                        """);
                yaml.dump(configuration, fileWriter);
            } catch (IOException io) {
                LOGGER.warn("Unable to write to file: " + file.getName());
                LOGGER.debug("File write IO error", io);
            }
        });
    }

    public CompletableFuture<Optional<Configuration>> read(File file) {
        return CompletableFuture.supplyAsync(() -> {
            Yaml yaml = new Yaml(new Constructor(Configuration.class, new LoaderOptions()));
            try (InputStream inputStream = new FileInputStream(file)) {
                return Optional.ofNullable(yaml.load(inputStream));
            } catch (IOException io) {
                LOGGER.warn("Unable to read file: " + file.getName());
                LOGGER.debug("File read IO error", io);
            } catch (Exception e) {
                LOGGER.debug("Unable to read file: " + file.getName() + ". Is it formatted correctly?", e);
            }

            return Optional.empty();
        });
    }

    public Optional<Configuration> getConfiguration() {
        return Optional.ofNullable(configuration);
    }

    public Configuration getDefaultConfiguration() {
        return new Configuration(
                "en-GB",
                true,
                Level.INFO,
                3
        );
    }
}
