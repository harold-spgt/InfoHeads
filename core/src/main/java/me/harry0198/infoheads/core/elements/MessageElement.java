package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.model.OnlinePlayer;

public final class MessageElement extends Element<String> {

    private String message;

    /**
     * Class Constructor
     * @param message Sets message of element
     */
    public MessageElement(final String message) {
        this.message = message;
    }

    /**
     * Sets message of element
     * @param message Message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the message of element
     * @return Message of element
     */
    public String getMessage() {
        return message;
    }

    @Override
    public void performAction(OnlinePlayer player) {
        player.sendMessage(removePlaceHolders(message, player));
    }

    @Override
    public String getContent() {
        return message;
    }


    @Override
    public InfoHeadType getType() {
        return InfoHeadType.MESSAGE;
    }
}
