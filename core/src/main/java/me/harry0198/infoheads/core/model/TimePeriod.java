package me.harry0198.infoheads.core.model;

import java.io.Serializable;

/**
 *
 * @param weeks
 * @param days
 * @param hours
 * @param minutes
 * @param seconds
 */
public record TimePeriod(
        int weeks,
        int days,
        int hours,
        int minutes,
        int seconds
) implements Serializable {
    public TimePeriod {
        // Copy constructor
        if (weeks < 0 || days < 0 || hours < 0 || minutes < 0 || seconds < 0
                || weeks > 60 || days > 60 || hours > 60 || minutes > 60 || seconds > 60) {
            throw new IllegalArgumentException("Cooldown field value boundaries must be between 0-60 (inclusive)");
        }
    }
    public long toMs() {
        long weeksCd = weeks * 604800000L;
        long daysCd = days * 86400000L;
        long hoursCd = hours * 3600000L;
        long minutesCd = minutes * 60000L;
        long secondsCd = seconds * 1000L;

        return weeksCd + daysCd + hoursCd + minutesCd + secondsCd;
    }

    public long toSeconds() {
        return toMs() / 1000;
    }
}
