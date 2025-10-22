package seedu.bitebuddy.model.foodplace;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.commons.util.AppUtil.checkArgument;
import static seedu.bitebuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Timing {

    public static final String MESSAGE_CONSTRAINTS = "Timing must be in the format HH:mm. "
            + "Closing time must be after or equal to opening time";

    public static final String MESSAGE_INVALID_TIME = "Invalid time provided. Time must be in HH:mm format";

    public static final String VALIDATION_REGEX = "^([01]?\\d|2[0-3]):[0-5]\\d$";

    private final LocalTime openingTime;
    private final LocalTime closingTime;
    

    public Timing(String timeRange) {
        requireNonNull(timeRange);
        checkArgument(isValidTiming(timeRange), MESSAGE_CONSTRAINTS);
        String[] parts = timeRange.split("-");
        String openStr = parts[0].trim();
        String closeStr = parts[1].trim();
        this.openingTime = LocalTime.parse(openStr);
        this.closingTime = LocalTime.parse(closeStr);
    }

    public Timing(String open, String close) {
        requireAllNonNull(open, close);
        checkArgument(isValidTime(open), MESSAGE_INVALID_TIME);
        checkArgument(isValidTime(close), MESSAGE_INVALID_TIME);
        this.openingTime = LocalTime.parse(open);
        this.closingTime = LocalTime.parse(close);
    }

    public Timing(LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public static boolean isValidTime(String time) {
        try {
            if (!time.matches(VALIDATION_REGEX)) {
                return false;
            }
            LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidTiming(String timeRange) {
        String[] parts = timeRange.split("-");
        if (parts.length != 2) {
            return false;
        }
        String openStr = parts[0].trim();
        String closeStr = parts[1].trim();
        if (!isValidTime(openStr) || !isValidTime(closeStr)) {
            return false;
        }
        LocalTime openingTime = LocalTime.parse(openStr);
        LocalTime closingTime = LocalTime.parse(closeStr);
        return !closingTime.isBefore(openingTime);
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
        return getOpeningTime().toString() + "-" + getClosingTime().toString();
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
