package seedu.bitebuddy.model.foodplace;

/**
 * Represents if a Foodplace is pinned in Bitebuddy
 */
public class Pinned {

    public final boolean isPinned;

    /**
     * Constructs a {@code Pinned}.
     *
     * @param isPinned a boolean.
     */
    public Pinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public boolean isPinned() {
        return this.isPinned;
    }

    @Override
    public String toString() {
        return String.valueOf(isPinned);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Pinned)) {
            return false;
        }

        Pinned otherPinned = (Pinned) other;
        return this.isPinned == otherPinned.isPinned;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isPinned);
    }
}
