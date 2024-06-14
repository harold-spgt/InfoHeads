package me.harry0198.infoheads.core.config;

public enum BundleMessages {
    PREFIX("prefix"),
    HELP("help"),
    NO_INFOHEAD_AT_LOCATION("infohead.missing"),
    INFOHEAD_REMOVED("infohead.removed"),
    USER_SHOULD_PLACE_INFOHEAD("infohead.place"),
    UNKNOWN_CMD("cmd.unknown"),
    FAILED_TO_ADD("infohead.failed-to-add");
    ;

    private final String key;

    BundleMessages(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
