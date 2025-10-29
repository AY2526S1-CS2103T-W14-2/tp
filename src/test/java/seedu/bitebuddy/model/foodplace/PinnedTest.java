package seedu.bitebuddy.model.foodplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PinnedTest {

    @Test
    public void constructor_validValue_success() {
        Pinned truePinned = new Pinned(true);
        assertTrue(truePinned.isPinned());

        Pinned falsePinned = new Pinned(false);
        assertFalse(falsePinned.isPinned());
    }

    @Test
    public void toString_returnsCorrectString() {
        Pinned truePinned = new Pinned(true);
        assertEquals("true", truePinned.toString());

        Pinned falsePinned = new Pinned(false);
        assertEquals("false", falsePinned.toString());
    }

    @Test
    public void equals() {
        Pinned pinnedTrue = new Pinned(true);
        Pinned pinnedFalse = new Pinned(false);

        // same object -> returns true
        assertTrue(pinnedTrue.equals(pinnedTrue));

        // same values -> returns true
        Pinned pinnedTrueCopy = new Pinned(true);
        assertTrue(pinnedTrue.equals(pinnedTrueCopy));

        // different types -> returns false
        assertNotEquals(pinnedTrue, true);

        // null -> returns false
        assertNotEquals(pinnedTrue, null);

        // different values -> returns false
        assertFalse(pinnedTrue.equals(pinnedFalse));
    }

    @Test
    public void hashCode_test() {
        Pinned pinnedTrue = new Pinned(true);
        Pinned pinnedTrueCopy = new Pinned(true);
        Pinned pinnedFalse = new Pinned(false);

        // same values -> same hashcode
        assertEquals(pinnedTrue.hashCode(), pinnedTrueCopy.hashCode());

        // different values -> different hashcode
        assertNotEquals(pinnedTrue.hashCode(), pinnedFalse.hashCode());
    }
}
