package me.harry0198.infoheads.core.ui;

import me.harry0198.infoheads.core.event.dispatcher.EventDispatcher;
import me.harry0198.infoheads.core.event.types.OpenMenuMenuEvent;
import me.harry0198.infoheads.core.persistence.entity.InfoHeadProperties;
import me.harry0198.infoheads.core.model.OnlinePlayer;
import me.harry0198.infoheads.core.model.TimePeriod;
import me.harry0198.infoheads.core.utils.SimpleProperty;

/**
 * The business logic for handling InfoHead cool downs.
 * Provides methods such as incrementing the cool downs, binding to the value and saving.
 */
public abstract class TimePeriodViewModel extends ViewModel {

    private final InfoHeadProperties configuration;
    private final SimpleProperty<TimePeriod> timePeriodProperty;

    /**
     * Constructs this class.
     * @param configuration Configuration which provides the {@link TimePeriod}.
     */
    protected TimePeriodViewModel(InfoHeadProperties configuration) {
        super(EventDispatcher.getInstance());
        this.configuration = configuration;
        this.timePeriodProperty = new SimpleProperty<>(new TimePeriod(0,0,0,0,0));
    }

    public abstract void initialize();

    /**
     * Sets the 'TimePeriod' configuration value to MS from the provided time parameters.
     * @param timePeriod {@link TimePeriod} to set for the InfoHead.
     */
    public void setTimePeriod(TimePeriod timePeriod) {
        timePeriodProperty.setValue(timePeriod);
    }

    /***
     * Gets the TimePeriod property to allow bindings and value retrieval.
     * @return the TimePeriod's {@link SimpleProperty}.
     */
    public SimpleProperty<TimePeriod> getTimePeriodProperty() {
        return timePeriodProperty;
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
    public abstract void saveConfiguration();

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
        EventDispatcher.getInstance().dispatchEvent(new OpenMenuMenuEvent(configuration, onlinePlayer));
    }

    /*
    Increments the TimePeriod by the given field.
    Restricts values to be between 0-60.
     */
    private void increment(int value, Field field) {
        TimePeriod timePeriod = getTimePeriodProperty().getValue();
        if (timePeriod == null) {
            timePeriod = new TimePeriod(0,0,0,0,0);
        }

        int updatedWeeks = timePeriod.weeks();
        int updatedDays = timePeriod.days();
        int updatedHours = timePeriod.hours();
        int updatedMinutes = timePeriod.minutes();
        int updatedSeconds = timePeriod.seconds();

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

        setTimePeriod(new TimePeriod(updatedWeeks, updatedDays, updatedHours, updatedMinutes, updatedSeconds));
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
