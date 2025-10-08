package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void equals() {
        Note note = new Note("Hello");

        // same object -> returns true
        assertTrue(note.equals(note));

        // same values -> returns true
        Note noteCopy = new Note(note.value);
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
