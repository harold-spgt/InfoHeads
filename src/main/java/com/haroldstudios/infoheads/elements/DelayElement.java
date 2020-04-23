package com.haroldstudios.infoheads.elements;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public final class DelayElement extends Element{

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
    public void performAction(Player player, PlayerInteractEvent event) {
        //Not needed - handled on interact
    }

    @Override
    public Object getContent() {
        return delayInSecs;
    }


    @Override
    public InfoHeadType getType() {
        return InfoHeadType.DELAY;
    }
}
