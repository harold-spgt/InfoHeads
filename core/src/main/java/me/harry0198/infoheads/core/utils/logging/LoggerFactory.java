package me.harry0198.infoheads.core.utils.logging;

import java.util.Objects;

public final class LoggerFactory {

    private static Logger logger;

    private LoggerFactory() {}

    public static void setLogger(Logger log) {
        logger = log;
    }

    public static Logger getLogger() {
        return Objects.requireNonNullElseGet(logger, DefaultLogger::new);
    }
}
