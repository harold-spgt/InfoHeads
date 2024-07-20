package me.harry0198.infoheads.core.utils.logging;

import java.util.Objects;

public final class LoggerFactory {

    private static Logger LOGGER;

    private LoggerFactory() {}

    public static void setLogger(Logger Logger) {
        LOGGER = Logger;
    }

    public static Logger getLogger() {
        return Objects.requireNonNullElseGet(LOGGER, DefaultLogger::new);
    }
}
