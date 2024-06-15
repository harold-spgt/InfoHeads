package me.harry0198.infoheads.core.config;

public enum BundleMessages {
    PREFIX("prefix"),
    HELP("help"),
    NO_PERMISSION("no-permission"),
    NO_INFOHEAD_AT_LOCATION("infohead.missing"),
    INFOHEAD_REMOVED("infohead.removed"),
    UNKNOWN_CMD("cmd.unknown"),
    INFOHEAD_ADDED("infohead.added"),
    PREVIOUS_PAGE("ui.previous-page"),
    COMPLETE_STEP("ui.complete"),
    INCREMENT_COOLDOWN("ui.cooldown.increment"),
    INCREMENT_COOLDOWN_MORE("ui.cooldown.increment-more"),
    DECREMENT_COOLDOWN("ui.cooldown.decrement"),
    DECREMENT_COOLDOWN_MORE("ui.cooldown.decrement-more"),
    COOLDOWN_TITLE("ui.title"),
    WEEKS("ui.weeks"),
    DAYS("ui.days"),
    HOURS("ui.hours"),
    MINUTES("ui.minutes"),
    SECONDS("ui.seconds"),
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
