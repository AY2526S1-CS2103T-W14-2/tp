package seedu.bitebuddy.model.foodplace;

import static java.util.Objects.requireNonNull;

/**
 * Represents if the foodplace is blacklisted in BiteBuddy.
 * Guarantees: immutable; is always valid
 */
public class Blacklist {
    public final Boolean isBlacklisted;

    /**
     * Constructs Blacklist object
     * @param blacklist the blacklist for a foodplace
     */
    public Blacklist(Boolean blacklist) {
        requireNonNull(blacklist);
        this.isBlacklisted = blacklist;
    }

    /**
     * Creates a Blacklist object with the opposite boolean
     * @return the blacklist object of the opposite boolean
     */
    public Blacklist getOpposite() {
        return new Blacklist(!isBlacklisted);
    }

    /**
     * Getter method for isBlacklisted
     * @return the isBlacklisted value
     */
    public Boolean isBlacklisted() {
        return isBlacklisted;
    }

    @Override
    public String toString() {
        return isBlacklisted ? "Blacklisted" : "Not blacklisted";
    }

    @Override
    public boolean equals(Object other) {
        // Check if same object reference
        if (this == other) {
            return true;
        }

        // Check if other is not a Blacklist or is null
        if (!(other instanceof Blacklist)) {
            return false;
        }

        // Safe to cast since we know it is a blacklist at this point and compare value
        Blacklist otherBlacklist = (Blacklist) other;
        return this.isBlacklisted.equals(otherBlacklist.isBlacklisted);
    }

    @Override
    public int hashCode() {
        return isBlacklisted.hashCode();
    }
}
