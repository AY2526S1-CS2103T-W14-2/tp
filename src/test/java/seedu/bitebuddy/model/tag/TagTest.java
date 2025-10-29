package seedu.bitebuddy.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.bitebuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void equals() {
        Tag tagA = new Tag("hawker");
        Tag tagACopy = new Tag("hawker");
        Tag tagB = new Tag("restaurant");

        // same values -> returns true
        assertTrue(tagA.equals(tagACopy));

        // same object -> returns true
        assertTrue(tagA.equals(tagA));

        // null -> returns false
        assertNotEquals(tagA, null);

        // different types -> returns false
        assertNotEquals(tagA, 5);

        // different tag -> returns false
        assertFalse(tagA.equals(tagB));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

}
