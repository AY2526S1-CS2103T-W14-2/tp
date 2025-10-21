package seedu.bitebuddy.model.foodplace;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.commons.util.AppUtil.checkArgument;

/**
 * Represents a Foodplace's cuisine in BiteBuddy.
 * Guarantees: immutable; is valid as declared in {@link #isValidCuisine(String)}
 */
public class Cuisine {
     public static final String MESSAGE_CONSTRAINTS =
            "Cuisine should only contain alphanumeric characters and spaces";

    /*
     * The first character of the cuisine must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String cuisineName;
    /**
     * Constructs a {@code Cuisine}.
     *
     * @param cuisine A valid cuisine.
     */
    public Cuisine(String cuisine) {
        requireNonNull(cuisine);
        checkArgument(isValidCuisine(cuisine), MESSAGE_CONSTRAINTS);
        this.cuisineName = cuisine;
    }

    /**
     * Returns true if a given string is a valid cuisine.
     */
    public static boolean isValidCuisine(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return cuisineName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Cuisine)) {
            return false;
        }

        Cuisine otherCuisine = (Cuisine) other;
        return cuisineName.equalsIgnoreCase(otherCuisine.cuisineName);
    }

    @Override
    public int hashCode() {
        return cuisineName.toLowerCase().hashCode();
    }
}
