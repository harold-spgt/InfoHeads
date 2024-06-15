package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.TimePeriod;

public final class DelayElement extends Element<TimePeriod> {

    private TimePeriod
            delayInSecs;

    /**
     * Class Constructor
     * @param delay of element
     */
    public DelayElement(final TimePeriod delay) {
        this.delayInSecs = delay;
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
    public void performAction(OnlinePlayer player) {
        //Not needed - handled on interact
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
