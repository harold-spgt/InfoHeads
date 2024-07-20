package me.harry0198.infoheads.core.config;

import me.harry0198.infoheads.core.utils.logging.Logger;
import me.harry0198.infoheads.core.utils.logging.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;

/**
 * A service that provides localized messages based on enums and locale settings.
 */
public class LocalizedMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String BUNDLE_NAME = "messages";
    private static final String LANGUAGE_FOLDER = "lang";
    private final ResourceBundle resourceBundle;
    private final UnaryOperator<String> colourReplaceStrategy;

    /**
     * Constructs a LocalizedMessageService for the given locale.
     *
     * @param locale The locale for which messages should be fetched.
     * @param colourReplaceStrategy Strategy to use to replace the colours.
     */
    public LocalizedMessageService(Locale locale, Path workingDirectory, UnaryOperator<String> colourReplaceStrategy) {
        this.resourceBundle = initializeResourceBundle(workingDirectory, locale);
        this.colourReplaceStrategy = colourReplaceStrategy;
    }

    public String prepare(String msg) {
        msg = msg.replace("@prefix", resourceBundle.getString(BundleMessages.PREFIX.getKey()));
        return colourReplaceStrategy.apply(msg);
    }

    public UnaryOperator<String> getColourReplaceStrategy() {
        return colourReplaceStrategy;
    }

    /**
     * Fetches a localized message based on the provided message key enum.
     *
     * @param messageKey The enum key representing the message to fetch.
     * @return The localized message as a string.
     */
    public String getMessage(BundleMessages messageKey) {
        return this.prepare(resourceBundle.getString(messageKey.getKey()));
    }
    public List<String> getMessageList(BundleMessages messageKey) {
        return Arrays.stream(resourceBundle.getString(messageKey.getKey()).split("\n")).map(this::prepare).toList();
    }

    public String getTimeMessage(Long milliseconds, BundleMessages bundleMessages) {

        String msg = getMessage(bundleMessages);
        long daysMillis = 0;
        long hoursMillis = 0;
        long minutesMillis = 0;
        long secondsMillis;

        if (msg.contains("@days")) {
            daysMillis = TimeUnit.DAYS.toMillis(TimeUnit.MILLISECONDS.toDays(milliseconds));
            msg = msg.replace("@days", String.valueOf(TimeUnit.MILLISECONDS.toDays(daysMillis)));
        }
        if (msg.contains("@hours")) {
            hoursMillis = TimeUnit.HOURS.toMillis(TimeUnit.MILLISECONDS.toHours(milliseconds - daysMillis));
            msg = msg.replace("@hours", String.valueOf(TimeUnit.MILLISECONDS.toHours(hoursMillis)));
        }
        if (msg.contains("@minutes")) {
            minutesMillis = TimeUnit.MINUTES.toMillis(TimeUnit.MILLISECONDS.toMinutes(milliseconds - daysMillis - hoursMillis));
            msg = msg.replace("@minutes", String.valueOf(TimeUnit.MILLISECONDS.toMinutes(minutesMillis)));
        }
        if (msg.contains("@seconds")) {
            secondsMillis = TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds - daysMillis - hoursMillis - minutesMillis));
            msg = msg.replace("@seconds", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(secondsMillis)));
        }

        return msg;
    }

    private ResourceBundle initializeResourceBundle(Path workingDirectory, Locale locale) {
        // Load language resource bundles from file system or throw.
        Path languageFolder = workingDirectory.resolve(LANGUAGE_FOLDER);

        URL url;
        try {
            url = languageFolder.toUri().toURL();
            copyFromJar("/resourcebundle", languageFolder);
        } catch (Exception e) {
            LOGGER.warn("Language folder URI malformed. Falling back to default resources.");
            LOGGER.debug("", e);
            return getDefaultResourceBundle(locale);
        }

        try (URLClassLoader loader = new URLClassLoader(new URL[]{url})) {
            return ResourceBundle.getBundle(BUNDLE_NAME, locale, loader);
        } catch (Exception e) {
            LOGGER.warn("Using internal locale.");
            LOGGER.debug("", e);
            return getDefaultResourceBundle(locale);
        }
    }

    private ResourceBundle getDefaultResourceBundle(Locale locale) {
        return ResourceBundle.getBundle("resourcebundle." + BUNDLE_NAME, locale);
    }

    //https://stackoverflow.com/questions/1386809/copy-directory-from-a-jar-file
    private void copyFromJar(String source, final Path target) throws URISyntaxException, IOException {
        URI resource = getClass().getResource("").toURI();

        try (FileSystem fileSystem = FileSystems.newFileSystem(
                resource,
                Collections.<String, String>emptyMap())
        ) {
            final Path jarPath = fileSystem.getPath(source);

            Files.walkFileTree(jarPath, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path currentTarget = target.resolve(jarPath.relativize(dir).toString());
                    Files.createDirectories(currentTarget);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        Files.copy(file, target.resolve(jarPath.relativize(file).toString()));
                    } catch (FileAlreadyExistsException ignore) {
                        // ignore
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}