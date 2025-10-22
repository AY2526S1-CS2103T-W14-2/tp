package seedu.bitebuddy.model.foodplace;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Timing {

    public static final String MESSAGE_CONSTRAINTS = "Timing must be in the format HH:mm. "
            + "Closing time must be after or equal to opening time";

    private final LocalTime openingTime;
    private final LocalTime closingTime;

    public Timing(LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public static boolean isValidTiming(LocalTime openingTime, LocalTime closingTime) {
        return !closingTime.isBefore(openingTime);
    }

    public static boolean isValidTiming(String openingTimeStr, String closingTimeStr) {
        try {
            LocalTime openingTime = LocalTime.parse(openingTimeStr);
            LocalTime closingTime = LocalTime.parse(closingTimeStr);
            return isValidTiming(openingTime, closingTime);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the opening time.
     */
    public LocalTime getOpeningTime() {
        return openingTime;
    }

    /**
     * Returns the closing time.
     */
    public LocalTime getClosingTime() {
        return closingTime;
    }

    /**
     * Checks if the foodplace is open at the given time.
     *
     * @param time The time to check.
     * @return true if the foodplace is open at the given time, false otherwise.
     */
    public boolean isOpenAt(LocalTime time) {
        return !time.isBefore(openingTime) && !time.isAfter(closingTime);
    }

    @Override
    public String toString() {
        return "Timing{" +
                "openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Timing)) {
            return false;
        }
        Timing otherTiming = (Timing) other;
        return openingTime.equals(otherTiming.openingTime) && closingTime.equals(otherTiming.closingTime);
    }

    @Override
    public int hashCode() {
        int result = openingTime.hashCode();
        result = 31 * result + closingTime.hashCode();
        return result;
    }
}
