package me.harry0198.infoheads.core.config;

public enum BundleMessages {
    PREFIX("prefix"),
    HELP("help"),
    NO_HEAD_FOUND("no-head-found"),
    NO_PERMISSION("no-permission"),
    NO_INFOHEAD_AT_LOCATION("infohead.missing"),
    INFOHEAD_REMOVED("infohead.removed"),
    UNKNOWN_CMD("cmd.unknown"),
    INFOHEAD_PLACE("infohead.place"),
    INFOHEAD_ADDED("infohead.added"),
    PREVIOUS_PAGE("ui.previous-page"),
    COMPLETE_STEP("ui.complete"),
    INCREMENT_COOLDOWN("ui.cooldown.increment"),
    INCREMENT_COOLDOWN_MORE("ui.cooldown.increment-more"),
    DECREMENT_COOLDOWN("ui.cooldown.decrement"),
    DECREMENT_COOLDOWN_MORE("ui.cooldown.decrement-more"),
    SET_LOCATION("ui.set-location"),
    SET_LOCATION_MORE("ui.set-location-more"),
    COOLDOWN_TITLE("ui.cooldown.title"),
    COOLDOWN_TIME("ui.cooldown.still"),
    ONE_TIME("one-time"),
    APPEND_NEW_ITEM("ui.add-new-action"),
    APPEND_MESSAGE_ELEMENT("ui.append.message"),
    APPEND_MESSAGE_ELEMENT_MORE("ui.append.message-more"),
    APPEND_CONSOLE_CMD_ELEMENT("ui.append.console-cmd"),
    APPEND_CONSOLE_CMD_ELEMENT_MORE("ui.append.console-cmd-more"),
    APPEND_PLAYER_CMD_ELEMENT("ui.append.player-cmd"),
    APPEND_PLAYER_CMD_ELEMENT_MORE("ui.append.player-cmd-more"),
    APPEND_DELAY_ELEMENT("ui.append.delay"),
    APPEND_DELAY_ELEMENT_MORE("ui.append.delay-more"),
    APPEND_TEMP_PERMISSION("ui.append.temp-perm"),
    APPEND_TEMP_PERMISSION_MORE("ui.append.temp-perm-more"),
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
    UI_MESSAGE_ELEMENT("ui.element.message"),
    UI_CONSOLE_CMD_ELEMENT("ui.element.console-cmd"),
    UI_PLAYER_CMD_ELEMENT("ui.element.player-cmd"),
    UI_TEMP_PERM_ELEMENT("ui.element.temp-permission"),
    UI_DELAY_ELEMENT("ui.element.delay"),
    UI_ELEMENT_MORE("ui.element.more"),
    UI_FORMAT_TIME("ui.format-time"),
    WEEKS("ui.weeks"),
    DAYS("ui.days"),
    HOURS("ui.hours"),
    MINUTES("ui.minutes"),
    SECONDS("ui.seconds"),
    FAILED_TO_ADD("infohead.failed-to-add"),
    FAILED_TO_REMOVE("infohead.failed-to-remove"),
    ONE_TIME_TITLE("ui.one-time.title"),
    ONCE_ITEM_LORE_ON("ui.one-time.lore.on"),
    ONCE_ITEM_LORE_OFF("ui.one-time.lore.off"),
    ADD_ACTION_GUI_TITLE("ui.add-action.title"),
    REQUEST_PERMISSION("input.permission"),
    CONVERSATION_TITLE("input.convo.title"),
    CONVERSATION_SUBTITLE("input.convo.subtitle"),
    REQUEST_PLAYER_PERMISSION("input.player-permission"),
    REQUEST_RENAME("input.rename"),
    REQUEST_CONSOLE_COMMAND("input.console-command"),
    REQUEST_PLAYER_COMMAND("input.player-command"),
    REQUEST_MESSAGE("input.message"),
    SAVE_FAILED("save-failed"),
    EDIT_INFOHEAD_UI_TITLE("ui.edit-ih.title");


    private final String key;

    BundleMessages(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
