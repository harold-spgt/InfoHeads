package me.harry0198.infoheads.core.service;

import me.harry0198.infoheads.core.config.BundleMessages;

import java.util.*;

/**
 * A service that provides localized messages based on enums and locale settings.
 */
public interface MessageService {


    String prepare(String msg);

    /**
     * Fetches a localized message based on the provided message key enum.
     *
     * @param messageKey The enum key representing the message to fetch.
     * @return The localized message as a string.
     */
    String getMessage(BundleMessages messageKey);
    List<String> getMessageList(BundleMessages messageKey);

    String getTimeMessage(Long milliseconds, BundleMessages bundleMessages);
}