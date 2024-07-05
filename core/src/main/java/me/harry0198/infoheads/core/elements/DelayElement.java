package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.TimePeriod;

import java.io.Serializable;

public final class DelayElement extends Element<TimePeriod> implements Serializable {

    private TimePeriod
            delayInSecs;

    /**
     * Class Constructor
     * @param delay of element
     */
    public DelayElement(final TimePeriod delay) {
        this.delayInSecs = delay;
    }

    public DelayElement(long delayInSeconds) {
        long weeks = delayInSeconds / (7 * 24 * 60 * 60);
        delayInSeconds %= (7 * 24 * 60 * 60);

        long days = delayInSeconds / (24 * 60 * 60);
        delayInSeconds %= (24 * 60 * 60);

        long hours = delayInSeconds / (60 * 60);
        delayInSeconds %= (60 * 60);

        long minutes = delayInSeconds / 60;
        delayInSeconds %= 60;

        long seconds = delayInSeconds;

        this.delayInSecs = new TimePeriod((int)weeks, (int)days, (int)hours, (int)minutes, (int)seconds);
    }

    /**
     * Sets delay of element
     * @param delay to set
     */
    public void setMessage(TimePeriod delay) {
        this.delayInSecs = delay;
    }

    /**
     * Gets the delay of element
     * @return Delay of element
     */
    public TimePeriod getDelay() {
        return delayInSecs;
    }

    @Override
    public void performAction(EventDispatcher eventDispatcher, OnlinePlayer player) {
        // Not needed - handled on interact
    }

    @Override
    public TimePeriod getContent() {
        return delayInSecs;
    }


    @Override
    public InfoHeadType getType() {
        return InfoHeadType.DELAY;
    }
}
