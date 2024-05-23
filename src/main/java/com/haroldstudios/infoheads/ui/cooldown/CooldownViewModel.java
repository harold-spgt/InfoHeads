package com.haroldstudios.infoheads.ui.cooldown;

import com.haroldstudios.infoheads.model.InfoHeadConfiguration;
import com.haroldstudios.infoheads.model.Cooldown;
import com.haroldstudios.infoheads.ui.SimpleProperty;

/**
 * The business logic for handling InfoHead cooldowns.
 * Provides methods such as incrementing the cooldown, binding to the value and saving.
 */
public final class CooldownViewModel {

    private final InfoHeadConfiguration configuration;
    private final SimpleProperty<Cooldown> cooldownProperty;

    /**
     * Constructs this class.
     * @param configuration Configuration which provides the {@link Cooldown}.
     */
    public CooldownViewModel(InfoHeadConfiguration configuration) {
        this.configuration = configuration;
        this.cooldownProperty = new SimpleProperty<>(configuration.getCooldown());
    }

    /**
     * Sets the 'cooldown' configuration value to MS from the provided time parameters.
     * @param cooldown {@link Cooldown} to set for the InfoHead.
     */
    public void setCooldown(Cooldown cooldown) {
        cooldownProperty.setValue(cooldown);
    }

    /***
     * Gets the cooldown property to allow bindings and value retrieval.
     * @return the cooldown's {@link SimpleProperty}.
     */
    public SimpleProperty<Cooldown> getCooldownProperty() {
        return cooldownProperty;
    }

    /**
     * Fetches the full configuration object.
     * @return {@link InfoHeadConfiguration}.
     */
    public InfoHeadConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Saves the current cooldown to the configuration state.
     */
    public void saveConfiguration() {
        configuration.setCooldown(cooldownProperty.getValue());
    }

    /**
     * Increments the cooldown by 1 {@link Field}. Does not allow incrementing over 60.
     * @param field to increment.
     */
    public void increment(Field field) {
        increment(1, field);
    }

    /**
     * Decrements the cooldown by 1 {@link Field}. Does not allow decrementing below 0.
     * @param field to decrement.
     */
    public void decrement(Field field) {
        increment(-1, field);
    }

    /*
    Increments the cooldown by the given field.
    Restricts values to be between 0-60.
     */
    private void increment(int value, Field field) {
        Cooldown cooldown = getCooldownProperty().getValue();
        if (cooldown == null) {
            cooldown = new Cooldown(0,0,0,0,0);
        }

        int updatedWeeks = cooldown.weeks();
        int updatedDays = cooldown.days();
        int updatedHours = cooldown.hours();
        int updatedMinutes = cooldown.minutes();
        int updatedSeconds = cooldown.seconds();

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

        setCooldown(new Cooldown(updatedWeeks, updatedDays, updatedHours, updatedMinutes, updatedSeconds));
    }

    /**
     * Field relating to the {@link Cooldown} field.
     */
    public enum Field {
        WEEKS,
        DAYS,
        HOURS,
        MINUTES,
        SECONDS
    }
}
