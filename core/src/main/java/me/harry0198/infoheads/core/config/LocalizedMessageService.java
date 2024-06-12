package me.harry0198.infoheads.core.config;

import me.harry0198.infoheads.core.domain.NotificationStrategy;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A service that provides localized messages based on enums and locale settings.
 */
public class LocalizedMessageService {

    private static final String BUNDLE_NAME = "messages";
    private final Locale locale;
    private final ResourceBundle resourceBundle;
    private final NotificationStrategy notificationStrategy;

    /**
     * Constructs a LocalizedMessageService for the given locale.
     *
     * @param notificationStrategy Strategy implementation for sending notifications.
     * @param locale The locale for which messages should be fetched.
     * @param baseName The base name of the resource bundle.
     */
    public LocalizedMessageService(NotificationStrategy notificationStrategy, Locale locale) {
        this.notificationStrategy = notificationStrategy;
        this.locale = locale;
        this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    /**
     * Gets the notification strategy object.
     * @return {@link NotificationStrategy} for notifications.
     */
    public NotificationStrategy getNotificationStrategy() {
        return notificationStrategy;
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
}