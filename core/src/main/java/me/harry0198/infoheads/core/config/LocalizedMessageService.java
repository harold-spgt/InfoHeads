package me.harry0198.infoheads.core.config;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A service that provides localized messages based on enums and locale settings.
 */
public class LocalizedMessageService {

    private static final String BUNDLE_NAME = "messages";
    private final ResourceBundle resourceBundle;

    /**
     * Constructs a LocalizedMessageService for the given locale.
     *
     * @param locale The locale for which messages should be fetched.
     */
    public LocalizedMessageService(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    /**
     * Fetches a localized message based on the provided message key enum.
     *
     * @param messageKey The enum key representing the message to fetch.
     * @return The localized message as a string.
     */
    public String getMessage(BundleMessages messageKey) {
        return resourceBundle.getString(messageKey.getKey());
    }
    public List<String> getMessageList(BundleMessages messageKey) {
        return List.of(resourceBundle.getString(messageKey.getKey()).split("\n"));
    }
}