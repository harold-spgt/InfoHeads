package me.harry0198.infoheads.core.config;

public enum BundleMessages {
    PREFIX("prefix"),
    HELP("help"),
    NO_PERMISSION("no-permission"),
    NO_INFOHEAD_AT_LOCATION("infohead.missing"),
    INFOHEAD_REMOVED("infohead.removed"),
    UNKNOWN_CMD("cmd.unknown"),
    INFOHEAD_ADDED("infohead.added"),
    FAILED_TO_ADD("infohead.failed-to-add"),
    FAILED_TO_REMOVE("infohead.failed-to-remove");
    ;

    private final String key;

    BundleMessages(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
