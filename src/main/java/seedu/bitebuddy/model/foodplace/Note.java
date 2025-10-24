package seedu.bitebuddy.model.foodplace;

import static java.util.Objects.requireNonNull;
import static seedu.bitebuddy.commons.util.AppUtil.checkArgument;

/**
 * Represents the notes about a foodplace in the address book.
 * Guarantees: immutable; is always valid
 */
public class Note {

    public static final String MESSAGE_CONSTRAINTS = "Note can only contain up to 100 characters max\n"
            + "Note should only contain ASCII-printable characters";
    // Allow printable ASCII characters only - space (32) to tilde (126)
    private static final String VALID_NOTE_REGEX = "^[ -~]*$";
    private static final int MAX_NOTE_LENGTH = 100;

    public final String value;

    /**
     * Constructs Note object, the string can be empty
     * @param note the note for a foodplace
     */
    public Note(String note) {
        requireNonNull(note);
        checkArgument(isValidNote(note), MESSAGE_CONSTRAINTS);
        this.value = note;
    }

    /**
     * Returns if a given string is a valid note.
     */
    public static boolean isValidNote(String test) {
        if (test.length() > MAX_NOTE_LENGTH) {
            return false;
        }
        return test.matches(VALID_NOTE_REGEX);
    }

    @Override
    public String toString() {
        return value.isEmpty() ? "No note" : value;
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
