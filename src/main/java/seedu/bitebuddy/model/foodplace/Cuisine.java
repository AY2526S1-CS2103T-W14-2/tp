package seedu.bitebuddy.model.foodplace;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.commons.util.AppUtil.checkArgument;

/**
 * Represents a Foodplace's cuisine in BiteBuddy.
 * Guarantees: immutable; is valid as declared in {@link #isValidCuisine(String)}
 */
public class Cuisine {
    public static final String MESSAGE_CONSTRAINTS =
        "Cuisine should contain only alphanumeric characters and spaces (empty allowed)";

    /*
    * Accepts alphanumeric characters and spaces. Allows empty string as valid.
    */
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]*";

    public final String value;
    /**
     * Constructs a {@code Cuisine}.
     *
     * @param cuisine A valid cuisine.
     */
    public Cuisine(String cuisine) {
        requireNonNull(cuisine);
        checkArgument(isValidCuisine(cuisine), MESSAGE_CONSTRAINTS);
        this.value = cuisine;
    }

    /**
     * Returns true if a given string is a valid cuisine.
     */
    public static boolean isValidCuisine(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
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
        return value.equalsIgnoreCase(otherCuisine.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }
}
