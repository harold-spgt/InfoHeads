package me.harry0198.infoheads.core.ui;

import me.harry0198.infoheads.core.event.EventDispatcher;
import me.harry0198.infoheads.core.event.inputs.OpenInfoHeadMenuEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.utils.SimpleProperty;

/**
 * The business logic for handling InfoHead cool downs.
 * Provides methods such as incrementing the cool downs, binding to the value and saving.
 */
public final class CoolDownViewModel {

    private final InfoHeadProperties configuration;
    private final SimpleProperty<TimePeriod> cooldownProperty;

    /**
     * Constructs this class.
     * @param configuration Configuration which provides the {@link TimePeriod}.
     */
    public CoolDownViewModel(InfoHeadProperties configuration) {
        this.configuration = configuration;

        // Ensure not null.
        if (configuration.getCoolDown() == null) {
            configuration.setCoolDown(new TimePeriod(0,0,0,0,0));
        }
        this.cooldownProperty = new SimpleProperty<>(configuration.getCoolDown());
    }

    /**
     * Sets the 'TimePeriod' configuration value to MS from the provided time parameters.
     * @param TimePeriod {@link TimePeriod} to set for the InfoHead.
     */
    public void setCoolDown(TimePeriod TimePeriod) {
        cooldownProperty.setValue(TimePeriod);
    }

    /***
     * Gets the TimePeriod property to allow bindings and value retrieval.
     * @return the TimePeriod's {@link SimpleProperty}.
     */
    public SimpleProperty<TimePeriod> getCoolDownProperty() {
        return cooldownProperty;
    }

    /**
     * Fetches the full configuration object.
     * @return {@link InfoHeadProperties}.
     */
    public InfoHeadProperties getConfiguration() {
        return configuration;
    }

    /**
     * Saves the current TimePeriod to the configuration state.
     */
    public void saveConfiguration() {
        configuration.setCoolDown(cooldownProperty.getValue());
    }

    /**
     * Increments the TimePeriod by 1 {@link Field}. Does not allow incrementing over 60.
     * @param field to increment.
     */
    public void increment(Field field) {
        increment(1, field);
    }

    /**
     * Decrements the TimePeriod by 1 {@link Field}. Does not allow decrementing below 0.
     * @param field to decrement.
     */
    public void decrement(Field field) {
        increment(-1, field);
    }

    public void navigateToPreviousPage(OnlinePlayer onlinePlayer) {
        EventDispatcher.getInstance().dispatchEvent(new OpenInfoHeadMenuEvent(configuration, onlinePlayer));
    }

    /*
    Increments the TimePeriod by the given field.
    Restricts values to be between 0-60.
     */
    private void increment(int value, Field field) {
        TimePeriod TimePeriod = getCoolDownProperty().getValue();
        if (TimePeriod == null) {
            TimePeriod = new TimePeriod(0,0,0,0,0);
        }

        int updatedWeeks = TimePeriod.weeks();
        int updatedDays = TimePeriod.days();
        int updatedHours = TimePeriod.hours();
        int updatedMinutes = TimePeriod.minutes();
        int updatedSeconds = TimePeriod.seconds();

        switch (field) {
            case WEEKS:
                updatedWeeks = Math.min(60, Math.max(0, updatedWeeks + value));
                break;
            case DAYS:
                updatedDays = Math.min(60, Math.max(0, updatedDays + value));
                break;
            case HOURS:
                updatedHours = Math.min(60, Math.max(0, updatedHours + value));
                break;
            case MINUTES:
                updatedMinutes = Math.min(60, Math.max(0, updatedMinutes + value));
                break;
            case SECONDS:
                updatedSeconds = Math.min(60, Math.max(0, updatedSeconds + value));
                break;
        }

        setCoolDown(new TimePeriod(updatedWeeks, updatedDays, updatedHours, updatedMinutes, updatedSeconds));
    }

    /**
     * Field relating to the {@link TimePeriod} field.
     */
    public enum Field {
        WEEKS,
        DAYS,
        HOURS,
        MINUTES,
        SECONDS
    }
}
