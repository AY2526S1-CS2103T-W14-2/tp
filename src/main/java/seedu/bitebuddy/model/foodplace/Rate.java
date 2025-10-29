package seedu.bitebuddy.model.foodplace;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.commons.util.AppUtil.checkArgument;

/**
 * Represents a Foodplace's Rate in the bitebuddy book.
 */
public class Rate {

    public static final Integer MAX = 10;
    public static final Integer MIN = 1;
    public static final Integer DEFAULT = 0;
    public static final String MESSAGE_CONSTRAINTS =
        String.format("Ratings should only contain numbers, and either: A) be an integer between %d to %d inclusive;"
            + " OR B) be %d to remove the target foodplace's existing rating.",
            MIN, MAX, DEFAULT
        );
    private final Integer value;

    /**
     * Constructs a {@code Rate} without a rating.
     */
    public Rate() {
        value = DEFAULT;
    }

    /**
     * Constructs a {@code Rate} using the provided rating.
     *
     * @param rating A valid Rate.
     */
    public Rate(Integer rating) {
        requireNonNull(rating);
        checkArgument(isValidRating(rating), MESSAGE_CONSTRAINTS);
        value = rating;
    }

    /**
     * Returns true if given rating is a valid rating score.
     */
    public static boolean isValidRating(Integer test) {
        return (test.equals(DEFAULT) || MIN <= test && test <= MAX);
    }

    /**
     * Gets the rating score.
     * @return Rating
     */
    public Integer getValue() {
        return this.value;
    }

    /**
     * Check if a rating has been set.
     */
    public boolean isSet() {
        return !(value.equals(DEFAULT));
    }

    @Override
    public String toString() {
        return String.format("< %d/%d >", value, MAX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rate)) {
            return false;
        }

        Rate otherRate = (Rate) other;
        return value.equals(otherRate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
