package seedu.bitebuddy.model.foodplace;

import static java.util.Objects.requireNonNull;

/**
 * Represents the notes about a foodplace in the address book.
 * Guarantees: immutable; is always valid
 */
public class Note {
    public final String value;

    /**
     * Constructs Note object, the string can be empty
     * @param note the note for a foodplace
     */
    public Note(String note) {
        requireNonNull(note);
        this.value = note;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && value.equals(((Note) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
