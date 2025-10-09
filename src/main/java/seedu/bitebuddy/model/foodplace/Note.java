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
        // Check if same object reference
        if (this == other) {
            return true;
        }

        // Check if other is not a Note or is null
        if (!(other instanceof Note)) {
            return false;
        }

        // Safe to cast since we know it is a note at this point and compare value
        Note otherNote = (Note) other;
        return this.value.equals(otherNote.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
