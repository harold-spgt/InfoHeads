package me.harry0198.infoheads.core.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;

/**
 * A service that provides localized messages based on enums and locale settings.
 */
public class LocalizedMessageService {

    private static final String BUNDLE_NAME = "resourcebundle.messages";
    private final ResourceBundle resourceBundle;
    private final UnaryOperator<String> colourReplaceStrategy;

    /**
     * Constructs a LocalizedMessageService for the given locale.
     *
     * @param locale The locale for which messages should be fetched.
     * @param colourReplaceStrategy Strategy to use to replace the colours.
     */
    public LocalizedMessageService(Locale locale, UnaryOperator<String> colourReplaceStrategy) {
        this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        this.colourReplaceStrategy = colourReplaceStrategy;
    }

    public String prepare(String msg) {
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

    public String getTimeMessage(Long milliseconds) {

        String msg = getMessage(BundleMessages.COOLDOWN_TIME);
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
}