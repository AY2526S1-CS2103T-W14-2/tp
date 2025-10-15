package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void hashCode_sameValue_sameHash() {
        Note n1 = new Note("hello");
        Note n2 = new Note("hello");
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    public void hashCode_differentValue_differentHash() {
        Note n1 = new Note("hello");
        Note n2 = new Note("bye");
        assertNotEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidNote_throwsIllegalArgumentException() {
        String invalidNote = "Nice place! \u0081";;
        assertThrows(IllegalArgumentException.class, () -> new Note(invalidNote));
    }

    @Test
    public void isValidNote() {
        // null email
        assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        assertTrue(Note.isValidNote("")); // empty string
        assertTrue(Note.isValidNote("    ")); // space-filled string
        assertTrue(Note.isValidNote("Hello"));

        assertFalse(Note.isValidNote("Nice place! \u0081")); // Contains non-printable character
        assertFalse(Note.isValidNote("a".repeat(150))); // Exceed character limit
    }

    @Test
    public void equals() {
        Note note = new Note("Hello");

        // same object -> returns true
        assertTrue(note.equals(note));

        // same values -> returns true
        Note noteCopy = new Note("Hello");
        assertTrue(note.equals(noteCopy));

        // different types -> returns false
        assertFalse(note.equals(1));

        // null -> returns false
        assertFalse(note.equals(null));

        // different note -> returns false
        Note differentNote = new Note("Bye");
        assertFalse(note.equals(differentNote));
    }
}
