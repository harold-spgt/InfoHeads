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
    APPEND_MESSAGE_ELEMENT("ui.append.message"),
    APPEND_MESSAGE_ELEMENT_MORE("ui.append.message-more"),
    APPEND_CONSOLE_CMD_ELEMENT("ui.append.console-cmd"),
    APPEND_CONSOLE_CMD_ELEMENT_MORE("ui.append.console-cmd-more"),
    APPEND_PLAYER_CMD_ELEMENT("ui.append.player-cmd"),
    APPEND_PLAYER_CMD_ELEMENT_MORE("ui.append.player-cmd-more"),
    APPEND_DELAY_ELEMENT("ui.append.delay"),
    APPEND_DELAY_ELEMENT_MORE("ui.append.delay-more"),
    SET_COOLDOWN("ui.set-cooldown"),
    SET_COOLDOWN_MORE("ui.set-cooldown-more"),
    VALID_PLACEHOLDERS("ui.placeholders"),
    VALID_PLACEHOLDERS_MORE("ui.placeholders-more"),
    PERMISSION_ELEMENT("ui.permission"),
    PERMISSION_ELEMENT_MORE("ui.permission-more"),
    EDIT_NAME("ui.edit-name"),
    EDIT_NAME_MORE("ui.edit-name-more"),
    CLOSE_WIZARD("ui.close"),
    CLOSE_WIZARD_MORE("ui.close-more"),

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
