package me.harry0198.infoheads.core.elements;

import me.harry0198.infoheads.core.model.OnlinePlayer;

public final class DelayElement extends Element<Integer> {

    private int delayInSecs;

    /**
     * Class Constructor
     * @param delay of element
     */
    public DelayElement(final int delay) {
        this.delayInSecs = delay;
    }

    /**
     * Sets delay of element
     * @param delay to set
     */
    public void setMessage(int delay) {
        this.delayInSecs = delay;
    }

    /**
     * Gets the delay of element
     * @return Delay of element
     */
    public int getDelay() {
        return delayInSecs;
    }

    @Override
    public void performAction(OnlinePlayer player) {
        //Not needed - handled on interact
    }

    @Override
    public Integer getContent() {
        return delayInSecs;
    }


    @Override
    public InfoHeadType getType() {
        return InfoHeadType.DELAY;
    }
}
